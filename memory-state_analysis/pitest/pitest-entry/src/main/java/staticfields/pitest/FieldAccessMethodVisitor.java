package staticfields.pitest;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class FieldAccessMethodVisitor extends MethodVisitor {

    private String className;
    FieldAccessMethodVisitor(int api, MethodVisitor methodVisitor, String className) {
        super(api, methodVisitor);
        this.className = className;
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        if (opcode == Opcodes.GETSTATIC || opcode == Opcodes.PUTSTATIC) {
            System.err.println("Found static field usage with owner: " + owner + " with name: "
                    + name + " with desc " + descriptor);
        }
        super.visitFieldInsn(opcode, owner, name, descriptor);
    }
}
