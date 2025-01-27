package stateprobe.pitest;
import org.pitest.coverage.TestInfo;
import org.pitest.mutationtest.MutationStatusTestPair;
import org.pitest.mutationtest.engine.Mutant;
import org.pitest.mutationtest.engine.MutationDetails;
//import org.pitest.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;


/**
 * This class is used to record the different glaring behaviors during the test execution
 * target/failingInOriginal.txt
 * target/mutatedDesc.txt
 */
public class ProblemRecorder {

    /**
     * This is add a line to the file that specifies which test in their original run fails
     * @param r original test result
     * @param mutatedClass class to be mutated
     * @param order which run? 1or two
     */
    public static synchronized void dumpOriginalFailingInfo(MutationStatusTestPair r, Mutant mutatedClass, int order, MutationDetails mid) {
        File targetFile = new File("target/failingInOriginal.txt");

        // Check if the file exists, and create it if it doesn't
        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                System.err.println("something went wrong in dumpOriginalFailingInfo in InstrumentationUtils.txt");
                throw new RuntimeException(e);
            }
        }

        for (String s: r.getKillingTests()) {
            // Append a line to the end of the file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFile, true))) {
                bw.write("mutation: " + mid.getId().hashCode() +  " test: " + s + " failed in the " + order + " original run.");
                bw.newLine();
            } catch (IOException e) {
                System.err.println("something went wrong in dumpOriginalFailingInfo in ProblemRecorder");
                e.printStackTrace();
            }
        }
    }

    public static synchronized void checkExistsOrCreate(String filePath) {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                System.err.println("something went wrong in checkExistsOrCreate In problemRecorder");
                throw new RuntimeException(e);
            }
        }
    }

    public static synchronized void addOneLine(String info, String filePath) {
        File targetFile = new File(filePath);
        // Check if the file exists, and create it if it doesn't
        checkExistsOrCreate(filePath);

        // Append a line to the end of the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFile, true))) {
            bw.write(info);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("something went wrong in addOneLine in ProblemRecorder");
            e.printStackTrace();
        }
    }

    public static synchronized void clearFile(String filePath) {
        checkExistsOrCreate(filePath);
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("");
        } catch (IOException e) {
            System.err.println("something went wrong in clearfile in ProblemRecorder");
            e.printStackTrace();
        }

    }

    public static synchronized String readOneLine(String filePath) {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            throw new RuntimeException(filePath + " doesn't exist in readOneLine");
        }
        try {

            // Create a new FileReader object that points to the text file
            FileReader reader = new FileReader(filePath);

            // Wrap the FileReader object in a BufferedReader object
            BufferedReader bufferedReader = new BufferedReader(reader);

            // Read a single line of text from the file
            String line = bufferedReader.readLine();

            // Close the file and release any resources associated with it
            bufferedReader.close();
            return line;
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
            System.err.println("DEBUG: something wrong in readOneLine, return value is null");
        }
        return null;
    }

    public static synchronized Set<String> getStaticFields() {
        Set<String> staticFields = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("target/staticFields.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                //filter JCL classes out (OWNER CLASS)
                String[] lineSplits = line.split(" ");
                if (true) {
                    staticFields.add(lineSplits[1] + " " + lineSplits[2] + " " + lineSplits[3]);
                }
            }
        } catch (IOException e) {
            System.err.println("fail to read static fields from target/staticFields.txt");
            return null;
        }
        return staticFields;
    }

    public static synchronized void initializeMRNMRProbes() {
        checkExistsOrCreate("target/NMR.txt");
        checkExistsOrCreate("target/MR.txt");
        clearFile("target/NMR.txt");
        clearFile("target/MR.txt");
        //for debugging intentionally leave one blank bit
        addOneLine("0 ", "target/NMR.txt");
        addOneLine("0 0 0 0", "target/MR.txt");
    }

    /**
     * invoked per test
     */
    public static synchronized void collectProbeInfo() {
        if (isMRState()) {
            checkExistsOrCreate("target/MRs.txt");
            String mr = readOneLine("target/MR.txt");
            // mr can be null due to test failure
            if (mr == null) {
                System.err.println("DEBUG: mr is null");
                addOneLine("-1 -1 -1 -1 -1", "target/MRs.txt");
            } else {
                addOneLine(mr, "target/MRs.txt");
            }
        } else if (isNMRState()) {
            checkExistsOrCreate("target/NMRs.txt");
            String nmr = readOneLine("target/NMR.txt");
            addOneLine(nmr, "target/NMRs.txt");
        }
    }

    public static synchronized boolean isMRState() {
        String res = readOneLine("target/GlobalStates.txt");
        if (res.equals("1")) {
            return true;
        }
        return false;
    }

    public static synchronized boolean isNMRState() {
        String res = readOneLine("target/GlobalStates.txt");
        if (res.equals("0")) {
            return true;
        }
        return false;
    }


    public static synchronized void setInitialState() {
        clearFile("target/GlobalStates.txt");
        addOneLine("2", "target/GlobalStates.txt");
        System.err.println("GLOBAL state");
    }

    public static synchronized void setMRState() {
        clearFile("target/testStatus.txt");
        clearFile("target/GlobalStates.txt");
        addOneLine("1", "target/GlobalStates.txt");
        System.err.println("MR state");
    }

    public static synchronized void setNullState() {
        clearFile("target/GlobalStates.txt");
        addOneLine("2", "target/GlobalStates.txt");
        System.err.println("finish mutation state");
    }

    public static synchronized void setNMRState() {
        clearFile("target/GlobalStates.txt");
        addOneLine("0", "target/GlobalStates.txt");
        System.err.println("NMR state");
    }

    /**
     * move states from MR or NMR, invoked in executeTests()
     * @param i
     * @param testName
     */
    public static synchronized void organizeStates(int i, String testName, long time,int len) {
        //clean folders at first

        if (isMRState()) {
            organizeStatesForMR(i,time);
        } else if (isNMRState()) {
            organizeStatesForNMR(i,time,testName,len);
        }

    }

    public static synchronized void organizeStatesForMR(int i,long time) {
        //clean MR
        String base = "target/xmlOutput/MR/" + i;

        //create MR folder
        try {
            File directory = new File(base);
            directory.mkdirs();
        } catch (Exception e) {
            addOneLine("fail to create folder","target/xmlOutput/errInfo.txt");
        }

        File file2 = new File("target/xmlOutput/states");
        if (file2.exists()) {
            moveDir("target/xmlOutput/states", base + "/states");
            clearFolder("target/xmlOutput/states");
        }

        File file_state_num = new File(base + "/states/state_num.txt");
        if (file_state_num.exists()) {
            moveFile(base + "/states/state_num.txt", base + "/state_num.txt");
        }

        addOneLine(String.valueOf(time), base + "/time.txt");
    }

    public static synchronized void organizeStatesForNMR(int i,long time,String name,int len) {
        int whichOriginalRun = getWhichOriginalRun();
        if (whichOriginalRun < 0) {
            ProblemRecorder.clearFolder("target/xmlOutput/states");
            return;
        }
        String base = "target/NMR/" + StaticData.NMRIndex + "/";
        if (whichOriginalRun == 0) {
            addOneLine(StaticData.NMRIndex + " " + name, "target/NMRIndex.txt");
            StaticData.NMRIndex = StaticData.NMRIndex + 1;
        } else {
            base = "target/NMR/" + (StaticData.NMRIndex - len + i)  + "/";
        }

        try {
            File directory = new File(base);
            directory.mkdirs();
        } catch (Exception e) {
            addOneLine("fail to create folder in organizeStatesForNMR","target/xmlOutput/errInfo.txt");
        }


        File file2 = new File("target/xmlOutput/states");
        if (file2.exists()) {
            new File(base + whichOriginalRun + "/states").mkdirs();
            moveDir("target/xmlOutput/states", base + whichOriginalRun + "/states");
            clearFolder("target/xmlOutput/states");
        }

        addOneLine(String.valueOf(time), base + "/time.txt");
    }


    public static synchronized void clearFolder(String folder) {
        try {
            Path directory = Paths.get(folder);
            Files.list(directory).forEach(path -> {
                if (Files.isDirectory(path)) {
                    clearFolder(path.toString());
                }
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    addOneLine("An error occurred while deleting the file: ","target/xmlOutput/errInfo.txt");
                }
            });
        } catch (IOException e) {
            System.err.println("something went wrong in clearFolder in ProblemRecorder");
        }
    }

    public static synchronized void setWhichTest(int i) {
        checkExistsOrCreate("target/WhichTest.txt");
        clearFile("target/WhichTest.txt");
        addOneLine(String.valueOf(i), "target/WhichTest.txt");
    }

    public static synchronized int getWhichTest() {
        return Integer.valueOf(readOneLine("target/WhichTest.txt"));
    }

    public static synchronized void setWhichOriginalRun(int i) {
        checkExistsOrCreate("target/WhichOriginalRun.txt");
        clearFile("target/WhichOriginalRun.txt");
        addOneLine(String.valueOf(i), "target/WhichOriginalRun.txt");
    }

    public static synchronized int getWhichOriginalRun() {
        return Integer.valueOf(readOneLine("target/WhichOriginalRun.txt"));
    }

    public static synchronized void createDirectory(String base) {
        try {
            File directory = new File(base);
            directory.mkdirs();
        } catch (Exception e) {
            addOneLine("fail to create folder","target/errInfo.txt");
        }
    }

    /**
     * for debugging, it might consume too much memory
     * collect states for all MR and NMR
     * clear MRs.txt and NMRs.txt
     */
    public static synchronized void collectCompleteInfo(MutationDetails mutation) {
        int hashID = mutation.getId().hashCode();
        String base = "target/everything/" + hashID;
        createDirectory(base);
        moveFile("target/MRs.txt", base + "/MRs.txt");
        moveFile("target/NMRs.txt", base + "/NMRs.txt");
        moveDir("target/xmlOutput/MR", base + "/MR");
        moveDir("target/xmlOutput/NMR", base + "/NMR");
        moveFile("target/testStatus.txt", base + "/testStatus.txt");
        moveFile("target/killingTests.txt", base + "/killingTests.txt");
        int index = 0;
        for (TestInfo t:mutation.getTestsInOrder()) {
            addOneLine(index + " " + t.getName(), base + "/testInfo.txt");
            index = index + 1;
        }

        String mutationInfo = mutation.getMutator() + " " + mutation.getFilename() + " " + mutation.getLineNumber()
                + " " + mutation.getTestsInOrder().size();
        addOneLine(mutationInfo, base + "/mutationInfo.txt");
        addOneLine(mutation.getDescription(), base + "/mutationInfo.txt");

    }

    /**
     * move File from sourceFile to desFile
     * make sure the sourceFile exists and there is nothing at desFile's path
     * @param sourceFile
     * @param desFile
     */
    public static synchronized void moveFile(String sourceFile, String desFile) {
        try {
            Path sourcePath = Paths.get(sourceFile);
            Path destPath = Paths.get(desFile);
            Files.move(sourcePath, destPath);
        } catch (IOException e) {
            addOneLine("An error occurred while moving the file from" + sourceFile + " to " + desFile, "target/errInfo.txt");
        }
    }

    public static synchronized void moveDir(String sourcePath, String desPath) {
        File sourceDir = new File(sourcePath);
        File destinationDir = new File(desPath);

        // Create the destination directory if it doesn't exist
        if (!destinationDir.exists()) {
            destinationDir.mkdirs();
        }

        // Get all files in the source directory
        File[] files = sourceDir.listFiles();
        for (File file : files) {
            // Construct the destination file path
            File destFile = new File(destinationDir, file.getName());
            // Move the file to the destination directory
            file.renameTo(destFile);
        }
    }

    public static synchronized void reportKillingTests(MutationStatusTestPair m) {
        clearFile("target/killingTests.txt");
        for (String s: m.getKillingTests()) {
            addOneLine(s, "target/killingTests.txt");
        }
    }

    /**
     * executed when a test starts
     */
    public static void setTestStart() {
        clearFile("target/startSignal.txt");
        addOneLine("1", "target/startSignal.txt");
    }

    /**
     * executed when onTestFailure is executed
     */
    public static void setTestSeenException() {
        clearFile("target/startSignal.txt");
        addOneLine("2", "target/startSignal.txt");
    }

    /**
     * check if the test has failed in onTestFailure
     * @return true if the test has failed
     */
    public static boolean hasFailed() {
        checkExistsOrCreate("target/startSignal.txt");
        String s = readOneLine("target/startSignal.txt");
        if (s == null) {
            addOneLine("1", "target/startSignal.txt");
        } else {
        }

        if (s != null && s.equals("2")) {
            return true;
        }
        return false;
    }
}
