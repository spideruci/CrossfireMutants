package org.example;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
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

            for (Path classFile : classFiles) {
                System.out.println("## " + classFile.toAbsolutePath().getFileName());

                Main tracker = new Main(classFile.toFile());
                ClassWriter writer = new MyClassWriter(ClassWriter.COMPUTE_FRAMES);
                ClassReader reader = new ClassReader(new FileInputStream(classFile.toFile()));
                reader.accept(new VariableTrackingClassVisitor(Opcodes.ASM9,writer), ClassReader.EXPAND_FRAMES);

                tracker.instrumentCode(classFile.toFile());
            }

        }

    }

    protected Main(File file) throws IOException {
        this.classFile = file;
        this.classReader = new ClassReader(new FileInputStream(this.classFile));
    }

    public byte[] fetchIntrumentedCode(File f) {
        ClassWriter writer = new MyClassWriter(ClassWriter.COMPUTE_FRAMES);
        classReader.accept(new TestClassVisitor(writer), ClassReader.EXPAND_FRAMES);
        return writer.toByteArray();
    }

    public void instrumentCode(File f) throws IOException {
        byte[] code = fetchIntrumentedCode(f);
        replaceOriginalCode(code);
    }


    public void replaceOriginalCode(byte[] code) throws IOException {
        PrintStream byteStream = new PrintStream(this.classFile.getAbsolutePath());
        byteStream.write(code);
        byteStream.close();
    }

}