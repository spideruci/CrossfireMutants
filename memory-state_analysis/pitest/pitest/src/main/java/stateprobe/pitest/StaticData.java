package stateprobe.pitest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class StaticData {

    public static Set<String> seenNMRs = new HashSet<>();

    public static int NMRIndex = 0;

    // Method to write data to a file
    public static void writeDataToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(filename, "UTF-8")) {
            for (String nmr : seenNMRs) {
                writer.println(nmr);
            }
            // Write the NMRIndex with a special marker
            writer.println("NMRIndex:" + NMRIndex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to read data from a file
    public static void readDataFromFile(String filename) {
        seenNMRs = new HashSet<>();
        NMRIndex = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("NMRIndex:")) {
                    NMRIndex = Integer.parseInt(line.split(":")[1]);
                } else {
                    seenNMRs.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
