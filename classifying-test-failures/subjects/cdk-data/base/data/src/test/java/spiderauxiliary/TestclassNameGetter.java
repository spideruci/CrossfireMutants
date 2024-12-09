package spiderauxiliary;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class TestclassNameGetter {

    public static Set<String> TestClassNames = new HashSet<>();

    static {
        File directory = new File("target/test-classes"); // Replace 'path_to_directory' with your directory path

        try {
            collectClassNames(directory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    private static void collectClassNames(File directory) throws IOException {
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                collectClassNames(file);
            } else if (file.getName().endsWith(".class") && !file.getAbsolutePath().toString().contains("ProxyAssertions")
                    && !file.getAbsolutePath().toString().contains("spiderauxiliary")) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ClassReader reader = new ClassReader(fis);
                    reader.accept(new ClassNameCollectorVisitor(), 0);
                }
            }
        }
    }
}


class ClassNameCollectorVisitor extends ClassVisitor {
    public ClassNameCollectorVisitor() {
        super(Opcodes.ASM9);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        TestclassNameGetter.TestClassNames.add(name.replace("/", "."));
    }
}