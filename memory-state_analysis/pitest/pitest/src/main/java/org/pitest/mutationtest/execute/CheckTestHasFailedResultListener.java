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

import java.util.ArrayList;
import java.util.List;
import org.pitest.mutationtest.DetectionStatus;
import org.pitest.testapi.Description;
import org.pitest.testapi.TestListener;
import org.pitest.testapi.TestResult;
import stateprobe.pitest.ProblemRecorder;

import static stateprobe.pitest.ProblemRecorder.addOneLine;
import static stateprobe.pitest.ProblemRecorder.isMRState;

public class CheckTestHasFailedResultListener implements TestListener {

  private final List<Description>   succeedingTests = new ArrayList<>();
  private final List<Description>   failingTests = new ArrayList<>();
  private final boolean       recordPassingTests;
  private int                 testsRun        = 0;

  public CheckTestHasFailedResultListener(boolean recordPassingTests) {
    this.recordPassingTests = recordPassingTests;
  }

  @Override
  public void onTestFailure(final TestResult tr) {
    if (ProblemRecorder.isMRState()) {
      tr.getThrowable().printStackTrace();

      StackTraceElement[] stackTraceElements = tr.getThrowable().getStackTrace();
      String info = "None";
      if (stackTraceElements.length != 0) {
        info = stackTraceElements[0].getClassName();
      }
      ProblemRecorder.addOneLine(tr.getThrowable().getClass().toString() + " " + info, "target/failingReasons.txt");
    } else {
      // ideally no tests would fail, but some tests fail due to polluted static fields.
      String info = "err: " + tr.getThrowable().getClass().getName() + tr.getThrowable().getMessage() + "\n";
      System.err.println("Original Failure");
      tr.getThrowable().printStackTrace();
      for (StackTraceElement ste: tr.getThrowable().getStackTrace()) {
        info = info + ste.getFileName() + " " + ste.getMethodName() + " " + ste.getLineNumber() + "\n";
      }
      ProblemRecorder.addOneLine(info, "target/failingInOriginalReasons.txt");
    }
    if (isMRState()) {
      addOneLine("FAIL","target/testStatus.txt");
    }

    this.failingTests.add(tr.getDescription());
  }

  @Override
  public void onTestSkipped(final TestResult tr) {

  }

  @Override
  public void onTestStart(final Description d) {
    this.testsRun++;
  }

  @Override
  public void onTestSuccess(final TestResult tr) {
    if (recordPassingTests) {
      this.succeedingTests.add(tr.getDescription());
    }
    if (isMRState()) {
      addOneLine("PASS","target/testStatus.txt");
    }
  }

  public DetectionStatus status() {
    if (!this.failingTests.isEmpty()) {
      return DetectionStatus.KILLED;
    } else {
      return DetectionStatus.SURVIVED;
    }
  }

  public List<Description> getSucceedingTests() {
    return succeedingTests;
}

  public List<Description> getFailingTests() {
    return failingTests;
}

  public int getNumberOfTestsRun() {
    return this.testsRun;
  }

  @Override
  public void onRunEnd() {

  }

  @Override
  public void onRunStart() {

  }

}
