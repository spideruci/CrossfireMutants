package spiderauxiliary;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;

public class ProbeRecord {

    public static Set<String> testRecord = new HashSet<>();

    public static Set<String> failRecord = new HashSet<>();

    public static synchronized void addRecord(String record) throws IOException {
        Set<String> s = readData();
        if (!s.contains(record)) {
            try {
                Files.write(Paths.get("target/testrecord.txt"), (record + " ").getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
        }
    }

    private static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static synchronized Set<String> readData() throws IOException {
        String filePath = "target/testrecord.txt";

        String str = null;
        try {
            str = readFile(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (str.length() == 0) {
            return new HashSet<>();
        }
        str = str.substring(0,str.length() - 1);
        Set<String> records = new HashSet<>();
        for (String i: str.split(" ")) {
            records.add(i);
        }
        return records;
    }

    public static synchronized void clearContent() {
        try {
            FileWriter fw = new FileWriter("target/testrecord.txt", false);
            PrintWriter pw = new PrintWriter(fw, false);
            pw.flush();
            pw.close();
            fw.close();
        } catch (Exception exception) {

        }
    }

    public static void createRecordFile() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("target/testrecord.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        writer.close();
    }

    public static boolean isTestOracleFailure(Throwable t) {
        if (t.toString().equals("org.opentest4j.AssertionFailedError")) {
            return true;
        } else if (t.toString().equals("java.lang.AssertionError")) {
            if (t.getStackTrace().length == 0) {
                return false;
            } else {
                if (t.getStackTrace()[0].toString().contains("junit.")) {
                    return true;
                } else {
                    if (testFileNames.contains(t.getStackTrace()[0].getFileName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void isTestFile() {

    }

    public static Set<String> getTestFiles() {
        String fileName = "LineNumbers.txt"; // Replace with your file path
        Set<String> results = new HashSet<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(".class")) {
                    // Extract content before ".class"
                    String beforeClass = line.split("\\.class")[0];

                    // If the extracted content contains "$", extract content before "$"
                    String beforeDollar = beforeClass.contains("$") ? beforeClass.split("\\$")[0] : beforeClass;

                    // Split with "/" and get the last string
                    String[] parts = beforeDollar.split("/");
                    String result = parts[parts.length - 1];
                    results.add(result + ".java");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Set<String> testFileNames = getTestFiles();

    public static void main(String[] args) {
        System.err.println("haah");
        getTestFiles();
    }

    public static boolean isAssertionError(Throwable t) {
        if ( t.getClass().getName().equals("org.opentest4j.AssertionFailedError")) {
            return true;
        } else if (t.getClass().getName().equals("java.lang.AssertionError")) {
            return true;
        } else if (t.getClass().getName().startsWith("org.mockito")) {
            return true;
        } else if (t.getClass().getName().startsWith("org.junit")) {
            return true;
        } else if (t.getClass().getName().startsWith("org.hamcrest")) {
            return true;
        } else if (t.getClass().getName().startsWith("junit.framework")) {
            return true;
        }
        return false;
    }
}

