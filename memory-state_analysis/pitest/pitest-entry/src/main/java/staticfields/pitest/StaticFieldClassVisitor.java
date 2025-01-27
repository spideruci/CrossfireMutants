/**
 * This class tracks the actual access of static fields for the whole program
 */
package staticfields.pitest;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import stateprobe.pitest.AsmUtils;

public class StaticFieldClassVisitor extends ClassVisitor {

    private String className;

    private int access;

    public StaticFieldClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM9, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = name;
        this.access = access;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {

        boolean isPrimitve = AsmUtils.isPrimitive(descriptor);
        boolean isFinal = (access & Opcodes.ACC_FINAL) != 0;
        boolean isStatic = (access & Opcodes.ACC_STATIC) != 0;
        //primitive value
        boolean shouldSkip = isPrimitve & ((access & Opcodes.ACC_FINAL) != 0);
        //final static string
        shouldSkip = shouldSkip | (descriptor.equals("Ljava/lang/String;") & isFinal & isStatic);
        shouldSkip = shouldSkip | descriptor.equals("Lorg/apache/commons/logging/Log;");
        //special cases
//        shouldSkip = true;
        if (((access & Opcodes.ACC_STATIC) != 0) && !shouldSkip) {
//            if (name.equals("cCache") || name.equals("cStyleCache") || name.equals("cPatternCache")
//                || name.equals("PATTERNS") || name.equals("FORMATTERS") || name.equals("cTypes")
//                || name.equals("DATE_DURATION_TYPES") || name.equals("TIME_DURATION_TYPES")
//                || name.equals("UBERSPECT") || name.equals("BOXING_CLASSES")
//                || name.equals("LOCALE_BEANS_BY_CLASSLOADER")
//                || name.equals("BEANS_BY_CLASSLOADER")
//                || name.equals( "LOCALE_BEANS_BY_CLASSLOADER")
//                || name.equals("CLASSLOADER_CACHE")
//                || name.equals("cache")
//                || name.equals("typeTransformers")
//                || name.equals("mapper")
//                || name.equals("logger")) {
                // mapper is for keycloak-core
                //logger is for openscience cdk
            if (false) {
//           if (className.contains("jfree") && (descriptor.equals("Ljava/util/ResourceBundle;") || descriptor.equals("Ljava/awt/Font;")
//                || descriptor.equals("Ljava/text/DateFormat;") || (name.equals("currentTheme") || name.equals("DEFAULT_SHAPE_SEQUENCE")
//                || name.equals("DEFAULT_ANCHOR_DATE") || descriptor.equals("Lorg/jfree/chart/renderer/xy/XYSplineRenderer$FillType;")) )) {
                //JFreeChart

                //1.ignore resourcebundle
                //2. all fonts are default fonts, so can safely be ignored
                //3. dateformat have default values
                //4. currentTheme is forced resetting in BeforeAll
                //5. DEFAULT_ANCHOR_DATE  changing when initialized
                //6. FillType enum type
                //7. DEFAULT_SHAPE_SEQUENCE
               // public static fields from public classes
            } else if ((access & Opcodes.ACC_PUBLIC) != 0 && (this.access & Opcodes.ACC_PUBLIC) != 0) {
                StaticInfoRecorder.fields.add("public" + " " + className + " " + name + " " + descriptor);
            } else {
//                StaticInfoRecorder.fields.add("non-public" + " " + className + " " + name + " " + descriptor);
            }
        }
        return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = this.cv.visitMethod(access, name, descriptor, signature, exceptions);
        return new FieldAccessMethodVisitor(Opcodes.ASM9,mv,this.className);
    }
}
