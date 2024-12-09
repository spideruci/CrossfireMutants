package org.example;

import org.objectweb.asm.*;

public class AssertionOverwritter extends ClassVisitor {

    public AssertionOverwritter(int api, ClassVisitor cv) {
        super(api, cv);
    }

    private String className;

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        // Add an annotation to the class
        this.className = name;

        if (name.endsWith("Test") || name.split("/")[name.split("/").length-1].startsWith("Test")) {
            AnnotationVisitor av = cv.visitAnnotation("Lorg/junit/jupiter/api/extension/ExtendWith;", true);
            // Add the TestExtension class value to the annotation
            AnnotationVisitor arrayVisitor = av.visitArray("value");
            arrayVisitor.visit(null, Type.getType("Lspiderauxiliary/TestExtension;"));
            arrayVisitor.visitEnd();
        }

        // Continue visiting the class as usual
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        return new AssertionMethodOverwritter(api, mv,this.className, name);
    }



    static class AssertionMethodOverwritter extends MethodVisitor {

        private int curLine = 0;

        private String className;

        private String methodName;

        /**
         * This method is called to instrument the assertion invocation in test code
         * ProxyAssertions and relevant helper classes are placed in subject's test package.
         * @param opcode
         * @param owner
         * @param name
         * @param descriptor
         * @param isInterface
         */
        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            if (owner.contains("junit") && name.equals("fail")) {
               Utils.writeFailInfo("target/failAssertion.txt", this.methodName + "-" + className + "-" + curLine);
            }

            if (Utils.isAssertionStatement(owner, name)) {
                visitLdcInsn(getFileName(className) + "-" + curLine);
                mv.visitMethodInsn(opcode, "ProxyAssertions/" + owner, name, insertBeforeLastOccurrence(descriptor, ")", "Ljava/lang/String;"), isInterface);
                AssertionCollector.assertions.add(owner + " " +  name + " " + descriptor);
                AssertionCollector.total_assertions.add(getFileName(className) + "-" + curLine);
                return;
            } else if (Utils.isMyAssertionStatement(owner,name)) {
                visitLdcInsn(getFileName(className) + "-" + curLine);
                mv.visitMethodInsn(opcode, "ProxyAssertions/customized/Customized", "runAssertion", insertBeforeLastOccurrence(descriptor, ")", "Ljava/lang/String;"), isInterface);
                AssertionCollector.total_assertions.add(getFileName(className) + "-" + curLine);
                return;
            }
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }

        @Override
        public void visitLineNumber(int line, Label start) {
            super.visitLineNumber(line, start);
            this.curLine = line;
        }

        public AssertionMethodOverwritter(int api, MethodVisitor mv, String className, String methodName) {
            super(api, mv);
            this.className = className;
            this.methodName = methodName;
        }

        public String getFileName(String s) {
            String temp = s.split("$")[0];
            String[] results = temp.split("/");
            return results[results.length - 1] + ".java";
        }

        public static String insertBeforeLastOccurrence(String input, String target, String insertion) {
            int lastIndex = input.lastIndexOf(target);
            if (lastIndex == -1) {
                return input;  // target not found in input
            }

            StringBuilder sb = new StringBuilder(input);
            sb.insert(lastIndex, insertion);
            return sb.toString();
        }

    }

}