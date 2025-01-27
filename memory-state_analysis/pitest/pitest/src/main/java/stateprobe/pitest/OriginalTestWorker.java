package stateprobe.pitest;

import org.pitest.mutationtest.MutationStatusTestPair;
import org.pitest.mutationtest.engine.Mutant;
import org.pitest.mutationtest.engine.MutationDetails;
import org.pitest.mutationtest.execute.CheckTestHasFailedResultListener;
import org.pitest.mutationtest.execute.HotSwap;
import org.pitest.testapi.Description;
import org.pitest.testapi.TestResult;
import org.pitest.testapi.TestUnit;
import org.pitest.testapi.execute.Container;
import org.pitest.testapi.execute.ExitingResultCollector;
import org.pitest.testapi.execute.Pitest;
import org.pitest.testapi.execute.containers.ConcreteResultCollector;
import org.pitest.testapi.execute.containers.UnContainer;
import org.pitest.util.Log;

//import java.io.PrintStream;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


import static org.pitest.util.Unchecked.translateCheckedException;
import static stateprobe.pitest.StaticData.NMRIndex;

public class OriginalTestWorker {
    private static final Logger LOG   = Log.getLogger();
    private static HotSwap hotswap;

    private static ClassLoader loader;
    public static void handleOriginal(
            final MutationDetails mutationId, final Mutant mutatedClass,
            final List<TestUnit> relevantTests,
            HotSwap hotswap, ClassLoader loader) {
        OriginalTestWorker.hotswap = hotswap;
        OriginalTestWorker.loader = loader;
        if ((relevantTests == null) || relevantTests.isEmpty()) {
            LOG.info(() -> "no covering tests");
        } else {
            LOG.info("running original code");
            handleCoveredOriginal(mutationId, mutatedClass, relevantTests);
        }
    }

    private static void handleCoveredOriginal(
            final MutationDetails mutationId, final Mutant mutatedClass,
            final List<TestUnit> relevantTests) {

        LOG.fine("" + relevantTests.size() + " relevant test for "
                    + mutatedClass.getDetails().getMethod());

        if (hotswap.insertClass(mutationId.getClassName(),loader, mutatedClass.getSource())) {
            doNOriginalRuns(10, relevantTests,mutatedClass, mutationId);
        } else {
            LOG.warning("fail to replace with the original source code");
        }
        LOG.fine("finished running original code");

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

    private static MutationStatusTestPair doTestsDetectMutation(final Container c,
                                                                final List<TestUnit> tests) {
        try {
            final CheckTestHasFailedResultListener listener = new CheckTestHasFailedResultListener(true);

            final Pitest pit = new Pitest(listener);

            pit.run(c, tests);

            return createStatusTestPair(listener);
        } catch (final Exception ex) {
            throw translateCheckedException(ex);
        }

    }

    private static MutationStatusTestPair createStatusTestPair(
            final CheckTestHasFailedResultListener listener) {
        List<String> failingTests = listener.getFailingTests().stream()
                .map(Description::getQualifiedName).collect(Collectors.toList());
        List<String> succeedingTests = listener.getSucceedingTests().stream()
                .map(Description::getQualifiedName).collect(Collectors.toList());

        return new MutationStatusTestPair(listener.getNumberOfTestsRun(),
                listener.status(), failingTests, succeedingTests);
    }

    private static void doNOriginalRuns(int N, final List<TestUnit> relevantTests, Mutant mutatedClass, final MutationDetails mid) {
        // just to fill out the cache.  Not sure if this is needed
        ProblemRecorder.setWhichOriginalRun(-1);
        doTestsDetectMutation(createNewContainer(), relevantTests);


        StaticData.readDataFromFile("target/globalData.txt");
        List<TestUnit> newRelevantTests = new LinkedList<TestUnit>();
        for (TestUnit t:relevantTests) {
            if (!StaticData.seenNMRs.contains(t.getDescription().getQualifiedName())) {
                newRelevantTests.add(t);
                StaticData.seenNMRs.add(t.getDescription().getQualifiedName());
            }
        }
        int startIndex = NMRIndex;

        if (!newRelevantTests.isEmpty()) {
            for (int runNum = 0; runNum < N; runNum++) {

                ProblemRecorder.setWhichOriginalRun(runNum);
                Container c = createNewContainer();
                MutationStatusTestPair pairResult = doTestsDetectMutation(c, newRelevantTests);
                //record flaky test runs
                //flaky test here refers to a test that sometimes pass while sometimes fail
                if (!pairResult.getKillingTests().isEmpty()) {
                    ProblemRecorder.dumpOriginalFailingInfo(pairResult, mutatedClass, runNum, mid);
                }
            }
        }

        for (int i = startIndex; i < NMRIndex; i++) {
            ZipFileManager.zipDir("target/NMR/" + i);
            ZipFileManager.deleteDirectory(new File("target/NMR/" + i));
        }

        StaticData.writeDataToFile("target/globalData.txt");
    }
}
