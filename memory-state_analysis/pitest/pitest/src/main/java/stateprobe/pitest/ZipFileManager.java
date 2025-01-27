package stateprobe.pitest;

import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

public class ZipFileManager {

    /**
     * it can fail to clean up everything sometimes, but we do not care.
     * @param directory
     */
    public static void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }

    public static void zipDir(String dir) {
        File directoryToZip = new File(dir);
        File zipFile = new File(dir + ".zip");
        ZipUtil.pack(directoryToZip, zipFile);
    }

}
