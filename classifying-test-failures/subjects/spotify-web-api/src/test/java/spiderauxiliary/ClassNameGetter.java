package spiderauxiliary;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static spiderauxiliary.ClassNameGetter.ClassNames;


public class ClassNameGetter {

    public static Set<String> ClassNames = new HashSet<>();

    static {
        File directory = new File("target/");

        try {
            collectClassNames(directory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * get all classNames in the target directory, avoid the ProxyAssertions and spiderauxiliary which contains our code
     * @param directory
     * @throws IOException
     */
    private static void collectClassNames(File directory) throws IOException {
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                collectClassNames(file);
            } else if (file.getName().endsWith(".class") && !file.getAbsolutePath().toString().contains("ProxyAssertions")
                    && !file.getAbsolutePath().toString().contains("spiderauxiliary")) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ClassReader reader = new ClassReader(fis);
                    reader.accept(new ClassNameVisitor(), 0);
                }
            }
        }
    }

    public static void printAllClassNames() {
        for(String name: ClassNames) {
            System.err.println(name);
        }
    }

}

class ClassNameVisitor extends ClassVisitor {
    public ClassNameVisitor() {
        super(Opcodes.ASM9);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        ClassNames.add(name.replace("/", "."));
    }
}

