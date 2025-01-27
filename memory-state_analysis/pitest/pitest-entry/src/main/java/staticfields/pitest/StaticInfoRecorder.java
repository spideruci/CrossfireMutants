package staticfields.pitest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class StaticInfoRecorder {
    public static Set<String> fields = new HashSet<String>();
    public static void writeOneStaticFieldToFile(String info) {
        File targetFile = new File("target/staticFields.txt");

        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Append a line to the end of the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFile, true))) {
            bw.write(info);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

