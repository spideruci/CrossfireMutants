package spiderauxiliary;

import org.junit.jupiter.api.extension.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;


public class TestExtension implements BeforeEachCallback, AfterEachCallback, BeforeAllCallback, AfterAllCallback {


    public static Set<String> executedAssertions = new HashSet<>();
    public static Set<String> failedAssertions = new HashSet<>();
    public static String firstFailedAssertion = "";
    public static String firstExecutedAssertion = "";
    public static long start_time = 0;
    public static long first_fail_time = 0;
    public static long end_time = 0;

    public static void initializePerTest() {
        executedAssertions = new HashSet<>();
        failedAssertions = new HashSet<>();
        firstFailedAssertion = "";
        firstExecutedAssertion  = "";
        end_time = 0;
        first_fail_time = 0;
        start_time = System.nanoTime();
    }



    @Override
    public void beforeEach(ExtensionContext context) {
        initializePerTest();
    }

    public static void writeToFile(String filePath, String content) {
        Path path = Paths.get(filePath);

        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            Files.write(path, content.getBytes(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void write_all_data() {

        String info = "";
        for (String s : executedAssertions) {
            info = info + " " + s;
        }
        if (info.length() != 0) {
            info = info.substring(1);
        }
        writeToFile("target/executedAssertions.txt", info);

        info = "";
        for (String s : failedAssertions) {
            info = info + " " + s;
        }
        if (info.length() != 0) {
            info = info.substring(1);
        }
        writeToFile("target/allAssertionFailures.txt", info);
        writeToFile("target/firstAssertionFailure.txt", firstFailedAssertion);
        writeToFile("target/firstExecutedAssertion.txt", firstExecutedAssertion);
        System.err.println("WTF: " + start_time + " " + first_fail_time + " " + end_time);
        writeToFile("target/timing.txt", start_time + " " + first_fail_time + " " + end_time);
    }


    public static void appendToFile(String filename, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(content);
            writer.newLine();  // Add a new line after the content.
        } catch (IOException e) {
            throw new  RuntimeException(e);
        }
    }

    public static void writePassingData(ExtensionContext context) {
        File f = new File("target/testAssertion.txt");

        try {
            f.createNewFile();
            appendToFile("target/testAssertion.txt","Test: " + context.getUniqueId());
            for (String s: executedAssertions) {
                appendToFile("target/testAssertion.txt",s);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void afterEach(ExtensionContext context) {

        end_time = System.nanoTime();
        write_all_data();
        System.err.println("aaaaaaaaaa: write data after each");
        // the test case fail
        if (!context.getExecutionException().isPresent() && failedAssertions.size() != 0) {
            throw new RuntimeException("this test case failed!");
        }
    }

    public static void recordFailedThrowable(String loc) {
        if (failedAssertions.size() == 0) {
            first_fail_time = System.nanoTime();
            firstFailedAssertion = loc;
        }
        failedAssertions.add(loc);
    }

    public static void recordExecutedOracle(String loc) {
        if (executedAssertions.size() == 0) {
            firstExecutedAssertion = loc;;
        }
        executedAssertions.add(loc);
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {

    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {

    }
}
