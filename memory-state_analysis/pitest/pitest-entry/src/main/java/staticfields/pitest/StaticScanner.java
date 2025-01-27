package staticfields.pitest;



import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.pitest.classinfo.ClassName;
import org.pitest.classpath.CodeSource;
import org.pitest.util.Log;

import java.util.HashSet;
import java.util.Set;


public class StaticScanner {
    public static void storeStaticFields(CodeSource code) {
        Log.getLogger().info("starting analyzing static fields in source code classes");
        System.err.println("code under test class name: ");
        Set<ClassName> allClassName = new HashSet<>();
        allClassName.addAll(code.getCodeUnderTestNames());

        for (ClassName cn: allClassName) {
            byte[] bytes = code.getBytes(cn.asInternalName()).get();
            ClassReader cr = null;
            cr = new ClassReader(bytes);
            ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
            ClassVisitor fieldFinder = new StaticFieldClassVisitor(cw);
            cr.accept(fieldFinder, 0);
        }
        for (String info:StaticInfoRecorder.fields) {
            StaticInfoRecorder.writeOneStaticFieldToFile(info);
        }

    }
}
