/*
 * Copyright 2010 Henry Coles
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package org.pitest.mutationtest.execute;

import org.apache.commons.codec.digest.DigestUtils;
import org.pitest.mutationtest.DetectionStatus;
import org.pitest.mutationtest.MutationStatusTestPair;
import org.pitest.mutationtest.build.PercentAndConstantTimeoutStrategy;
import org.pitest.mutationtest.engine.Mutant;
import org.pitest.mutationtest.engine.Mutater;
import org.pitest.mutationtest.engine.MutationDetails;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.mocksupport.JavassistInterceptor;
import org.pitest.testapi.Description;
import org.pitest.testapi.TestResult;
import org.pitest.testapi.TestUnit;
import org.pitest.testapi.execute.Container;
import org.pitest.testapi.execute.ExitingResultCollector;
import org.pitest.testapi.execute.MultipleTestGroup;
import org.pitest.testapi.execute.Pitest;
import org.pitest.testapi.execute.containers.ConcreteResultCollector;
import org.pitest.testapi.execute.containers.UnContainer;
import org.pitest.util.Log;
import stateprobe.pitest.OriginalTestWorker;
import stateprobe.pitest.ProblemRecorder;
import stateprobe.pitest.ZipFileManager;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.pitest.util.Unchecked.translateCheckedException;
import static stateprobe.pitest.ProblemRecorder.addOneLine;
import static stateprobe.pitest.ProblemRecorder.checkExistsOrCreate;
import static stateprobe.pitest.ProblemRecorder.clearFile;
import static stateprobe.pitest.ProblemRecorder.clearFolder;
import static stateprobe.pitest.ProblemRecorder.collectCompleteInfo;
import static stateprobe.pitest.ProblemRecorder.createDirectory;
import static stateprobe.pitest.ProblemRecorder.moveFile;
import static stateprobe.pitest.ProblemRecorder.setMRState;
import static stateprobe.pitest.ProblemRecorder.setNMRState;
import static stateprobe.pitest.ProblemRecorder.setNullState;

public class MutationTestWorker {

  private static final Logger                               LOG   = Log
      .getLogger();

  // micro optimise debug logging
  private static final boolean                              DEBUG = LOG
      .isLoggable(Level.FINE);

  private final Mutater                                     mutater;
  private final ClassLoader                                 loader;
  private final HotSwap                                     hotswap;
  private final boolean                                     fullMutationMatrix;

  public MutationTestWorker(
      final HotSwap hotswap,
      final Mutater mutater, final ClassLoader loader, final boolean fullMutationMatrix) {
    this.loader = loader;
    this.mutater = mutater;
    this.hotswap = hotswap;
    this.fullMutationMatrix = fullMutationMatrix;
  }

  protected void run(final Collection<MutationDetails> range, final Reporter r,
                     final TimeOutDecoratedTestSource testSource, List<TestUnit> tests) throws IOException {
    System.err.println("Report: there are " + range.size() + "mutants to be run");
    for (final MutationDetails mutation : range) {
      if (DEBUG) {
        LOG.fine("Running mutation " + mutation);
      }
      System.err.println("mutation hash code start: " + mutation.getId().hashCode());

      //create MR and NMR directory, clear everything
      createDirectory("target/xmlOutput/MR");
      createDirectory("target/xmlOutput/NMR");
      clearFolder("target/xmlOutput/MR");
      clearFolder("target/xmlOutput/NMR");
      clearFile("target/MRs.txt");
      clearFile("target/NMRs.txt");
      clearFile("target/xmlOutput/states_num.txt");
      clearFile("target/failingReasons.txt");
      clearFile("target/killingTests.txt");
      clearFile("target/testStatus.txt");
      clearFile("target/status.txt");



      final long t0 = System.currentTimeMillis();
      processMutation(r, testSource, mutation, tests);
      //one mutation finished running, process results
      System.err.println("12345678901: " + mutation.getId().hashCode());
      collectCompleteInfo(mutation);
      checkExistsOrCreate("target/status.txt");
      moveFile("target/status.txt", "target/everything/" + mutation.getId().hashCode() + "/status.txt");
      checkExistsOrCreate("target/failingReasons.txt");
      moveFile("target/failingReasons.txt", "target/everything/" + mutation.getId().hashCode() + "/failingReasons.txt");
      String base = "target/everything/" + mutation.getId().hashCode();
      ZipFileManager.zipDir(base);
      System.err.println("I'm zipping" + base);
      ZipFileManager.deleteDirectory(new File(base));
      if (DEBUG) {
        LOG.fine("processed mutation in " + (System.currentTimeMillis() - t0)
            + " ms.");
      }
      System.err.println("mutation id hashcode: " + mutation.getId().hashCode());
    }

  }

  private void processMutation(final Reporter r,
                               final TimeOutDecoratedTestSource testSource,
                               final MutationDetails mutationDetails, List<TestUnit> tests) {

    //clean target/mutatedDesc.txt File
    ProblemRecorder.clearFile("target/mutatedDesc.txt");
    ProblemRecorder.addOneLine("ha ha ha","target/mutatedDesc.txt");

    final MutationIdentifier mutationId = mutationDetails.getId();
    final Mutant mutatedClass = this.mutater.getMutation(mutationId);

    // For the benefit of mocking frameworks such as PowerMock
    // mess with the internals of Javassist so our mutated class
    // bytes are returned
    JavassistInterceptor.setMutant(mutatedClass);
    //how it works?
    System.err.println("INFO1: " + mutationDetails.getTestsInOrder().size() + "tests");
    final List<TestUnit> relevantTests = testSource
        .translateTests(mutationDetails.getTestsInOrder());
    System.err.println("INFO2: " + relevantTests.size() + "tests");

    //give it enough time for NMR to run
    TimeOutDecoratedTestSource testOriginalSource = new TimeOutDecoratedTestSource(new PercentAndConstantTimeoutStrategy(100, 60000), tests, null);
    final List<TestUnit> relevantOriginalTests = testOriginalSource
            .translateTests(mutationDetails.getTestsInOrder());

    r.describe(mutationId);

    if (DEBUG) {
      LOG.fine("mutating method " + mutatedClass.getDetails().getMethod());
    }

    //do ten original run without mutations
    setNMRState();
    // in NMR, tests are not decorated with "time out".
    OriginalTestWorker.handleOriginal(mutationDetails, mutatedClass, relevantOriginalTests, this.hotswap, this.loader);
    setNullState();

    //do mutation run first, then 10 original runs
    setMRState();
    final MutationStatusTestPair mutationDetected = handleMutation(
            mutationDetails, mutatedClass, relevantTests);

    r.report(mutationId, mutationDetected);
    if (DEBUG) {
      LOG.fine("Mutation " + mutationId + " detected = " + mutationDetected);
    }
    ProblemRecorder.reportKillingTests(mutationDetected);
    addOneLine(mutationDetected.getStatus().name(), "target/status.txt");

  }

  private MutationStatusTestPair handleMutation(
      final MutationDetails mutationId, final Mutant mutatedClass,
      final List<TestUnit> relevantTests) {
    final MutationStatusTestPair mutationDetected;
    if ((relevantTests == null) || relevantTests.isEmpty()) {
      LOG.info(() -> "No test coverage for mutation " + mutationId + " in "
          + mutatedClass.getDetails().getMethod());
      mutationDetected =  MutationStatusTestPair.notAnalysed(0, DetectionStatus.RUN_ERROR);
    } else {
      mutationDetected = handleCoveredMutation(mutationId, mutatedClass,
          relevantTests);

    }
    return mutationDetected;
  }

  private String generateHashFromByteArray(byte[] dataToHash) throws NoSuchAlgorithmException {
    return DigestUtils.sha256Hex(dataToHash);
  }

  private MutationStatusTestPair handleCoveredMutation(
      final MutationDetails mutationId, final Mutant mutatedClass,
      final List<TestUnit> relevantTests) {
    final MutationStatusTestPair mutationDetected;
    if (DEBUG) {
      LOG.fine("" + relevantTests.size() + " relevant test for "
          + mutatedClass.getDetails().getMethod());
    }

    final Container c = createNewContainer();
    final long t0 = System.currentTimeMillis();

    if (this.hotswap.insertClass(mutationId.getClassName(), this.loader,
        mutatedClass.getBytes())) {
      try {
        String mut = generateHashFromByteArray(mutatedClass.getBytes());
        String org = generateHashFromByteArray(mutatedClass.getSource());
        ProblemRecorder.addOneLine(mutationId.hashCode() + " " + mut + " " + org, "target/test-classes/bytes.txt");
      } catch (Throwable t) {
        t.printStackTrace();
        System.err.println("hashing error");
      }

      if (DEBUG) {
        LOG.fine("replaced class with mutant in "
            + (System.currentTimeMillis() - t0) + " ms");
      }


      mutationDetected = doTestsDetectMutation(c, relevantTests);
    } else {
      LOG.warning("Mutation " + mutationId + " was not viable ");
      mutationDetected = MutationStatusTestPair.notAnalysed(0,
          DetectionStatus.NON_VIABLE);
    }

    return mutationDetected;
  }

  private static Container createNewContainer() {
    return new UnContainer() {
      @Override
      public List<TestResult> execute(final TestUnit group) {
        final List<TestResult> results = new ArrayList<>();
        final ExitingResultCollector rc = new ExitingResultCollector(
            new ConcreteResultCollector(results));
        group.execute(rc);
        return results;
      }
    };
  }



  @Override
  public String toString() {
    return "MutationTestWorker [mutater=" + this.mutater + ", loader="
        + this.loader + ", hotswap=" + this.hotswap + "]";
  }

  private MutationStatusTestPair doTestsDetectMutation(final Container c,
      final List<TestUnit> tests) {
    try {
      final CheckTestHasFailedResultListener listener = new CheckTestHasFailedResultListener(fullMutationMatrix);

      final Pitest pit = new Pitest(listener);
      
      if (this.fullMutationMatrix) {
        pit.run(c, tests);
      } else {
        pit.run(c, createEarlyExitTestGroup(tests));
      }

      return createStatusTestPair(listener);
    } catch (final Exception ex) {
      throw translateCheckedException(ex);
    }

  }

  private MutationStatusTestPair createStatusTestPair(
      final CheckTestHasFailedResultListener listener) {
    List<String> failingTests = listener.getFailingTests().stream()
        .map(Description::getQualifiedName).collect(Collectors.toList());
    List<String> succeedingTests = listener.getSucceedingTests().stream()
        .map(Description::getQualifiedName).collect(Collectors.toList());

    return new MutationStatusTestPair(listener.getNumberOfTestsRun(),
        listener.status(), failingTests, succeedingTests);
  }

  private List<TestUnit> createEarlyExitTestGroup(final List<TestUnit> tests) {
    return Collections.singletonList(new MultipleTestGroup(tests));
  }

}
