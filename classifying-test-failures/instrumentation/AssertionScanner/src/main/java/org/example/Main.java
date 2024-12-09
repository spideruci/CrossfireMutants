package org.example;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    final public File classFile;
    final public ClassReader classReader;
    public static void main(String[] args) throws IOException {

        if (args.length <= 0) {
            return;
        }

        File file = new File(args[0]);

        if (!file.exists()) {
            final String errorMessage = MessageFormat.format("file does not exist: {0}", file.getName());
            System.err.println(errorMessage);
            new RuntimeException(errorMessage);
        }

        if (file.isDirectory()) {
            // do nothing
            System.err.println(MessageFormat.format("Directory: {0}", args[0]));
            List<Path> classFiles;

            try (Stream<Path> fileStream = Files.walk(file.toPath())) {
                classFiles = fileStream.filter(Files::isRegularFile)
                        .filter(p -> p.toFile().getName().endsWith(".class"))
                        .collect(Collectors.toList());
            }

            PrintWriter writer = null;
            try {
                writer = new PrintWriter("target/LineNumbers.txt", "UTF-8");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            writer.close();


            for (Path classFile : classFiles) {
                System.out.println("## " + classFile.toAbsolutePath().toString());
                if (classFile.toAbsolutePath().toString().contains("ProxyAssertions")
                        || classFile.toAbsolutePath().toString().contains("spiderauxiliary")) {
                    System.err.println("skip");
                    continue;
                }

                Main tracker = new Main(classFile.toFile());
                tracker.addTryCatch();
            }
            printAssertions();

        }

    }

    public static void printAssertions() {
        Path path = Paths.get("target/InstrumentedAssertions.txt");

        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
                System.out.println("File created successfully!");
            } else {
//                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.err.println("Error creating the file: " + e.getMessage());
        }

        try {
            for (String assertion: AssertionCollector.assertions) {
                Files.write(path,  (assertion + "\n").getBytes(), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            System.err.println("wrong here");
            //exception handling left as an exercise for the reader
        }


        path = Paths.get("target/totalAssertions.txt");

        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
                System.out.println("File created successfully!");
            } else {
//                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.err.println("Error creating the file: " + e.getMessage());
        }

        try {
            for (String assertion: AssertionCollector.total_assertions) {
                Files.write(path,  (assertion + "\n").getBytes(), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            System.err.println("wrong here");
            //exception handling left as an exercise for the reader
        }
    }





    protected Main(File file) throws IOException {
        this.classFile = file;
        this.classReader = new ClassReader(new FileInputStream(this.classFile));
    }

    public void replaceOriginalCode(byte[] code) throws IOException {
        PrintStream byteStream = new PrintStream(this.classFile.getAbsolutePath());
        byteStream.write(code);
        byteStream.close();
    }

    public void addTryCatch() throws IOException {
        ClassWriter writer = new MyClassWriter(ClassWriter.COMPUTE_FRAMES);
        AssertionOverwritter to = new AssertionOverwritter(Opcodes.ASM9, writer);
        new ClassReader(new FileInputStream(this.classFile)).accept(to, ClassReader.EXPAND_FRAMES);

        replaceOriginalCode(writer.toByteArray());

    }

}