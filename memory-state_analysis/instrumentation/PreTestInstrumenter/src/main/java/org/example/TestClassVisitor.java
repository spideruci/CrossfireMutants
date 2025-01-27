package org.example;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.LinkedList;
import java.util.List;

public class TestClassVisitor extends ClassVisitor {

    private String className;

    private boolean isStatic;

    private List<String> fieldInfos = new LinkedList<String>();

    public TestClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM9, cv);
        this.className = null;
        this.isStatic = false;
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        if ((access & Opcodes.ACC_STATIC) == 0) {  // Check if the field is non-static
            // ignore inner classes
            if (!className.contains("$")) {
                fieldInfos.add(className + " " + name + " " + descriptor);
            }
        }
        return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = this.cv.visitMethod(access, name, descriptor, signature, exceptions);
        boolean hasThis;
        if ((access & Opcodes.ACC_STATIC) == 0) {
            hasThis = true;
        } else {
            hasThis = false;
        }
        if (this.className.startsWith("inst/InstrumentationUtils") | name.equals("<clinit>")
            | className.contains("spotify/TestUtil")) {
            // this is only for spotify-web-api, as this is a utility class
            return mv;
        }

        return new TestMethodVisitor(Opcodes.ASM9,mv,access, name, descriptor, className,hasThis, fieldInfos);
    }

}