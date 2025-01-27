package org.example;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Label;
import org.objectweb.asm.commons.AdviceAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.example.VariableTrackingClassVisitor.localVariableInfo;


public class TestMethodVisitor extends AdviceAdapter {

    private int curline;

    private boolean isBeforeAll;

    private boolean isAfterAll;

    private String methodName;

    private String className;

    private boolean hasThis;

    private boolean isTest;

    private List<String> fieldInfo;

    private ArrayList<VisitedInsn> insns = new ArrayList<>();

    private List<LocalVariable> localVars = null;

    private Label label;

    List<LocalVariable> temp;

    protected TestMethodVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor, String className, boolean classAccess, List<String> fieldInfo) {
        super(api, methodVisitor, access, name, descriptor);
        this.methodName = name;
        this.className = className;
        this.hasThis = classAccess;
        this.fieldInfo = fieldInfo;
        this.localVars = localVariableInfo.get(name + descriptor + access);
        temp = VariableTrackingClassVisitor.result.get(name + descriptor + access);
        System.err.println(temp + "haha");
    }

    @Override
    public void visitLabel(Label label) {

        insns.add(VisitedInsn.makeLabel(label));
        this.label = label;
        super.visitLabel(label);
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        curline = line;
        super.visitLineNumber(line, start);
    }
    /**
     * initailize probes only in beforeall
     */

    @Override
    public void visitCode() {
        super.visitCode();
        if (this.isBeforeAll) {

            // instantiate the object list
            mv.visitTypeInsn(Opcodes.NEW, "java/util/LinkedList");
            mv.visitInsn(Opcodes.DUP);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/LinkedList", "<init>", "()V", false);
            mv.visitFieldInsn(Opcodes.PUTSTATIC, "inst/InstrumentationUtils", "afterAllStates", "Ljava/util/List;");

            // instantiate the object type list
            mv.visitTypeInsn(Opcodes.NEW, "java/util/LinkedList");
            mv.visitInsn(Opcodes.DUP);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/LinkedList", "<init>", "()V", false);
            mv.visitFieldInsn(Opcodes.PUTSTATIC, "inst/InstrumentationUtils", "afterAllStatesInfo", "Ljava/util/List;");
        }

        if (this.isTest) {
            System.out.println("add isTest");
            // add "this" here
            if (hasThis) {

                // Load the 'afterAllStates' static field onto the stack
                mv.visitFieldInsn(Opcodes.GETSTATIC, "inst/InstrumentationUtils", "afterAllStates", "Ljava/util/List;");
                // Invoke the 'size' method of the List interface
                mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "size", "()I", true);
                // Push the constant 1050 onto the stack
                mv.visitLdcInsn(10050);
                // Compare the size of the list with 1050, jump if the condition is met
                Label label = new Label();
                mv.visitJumpInsn(Opcodes.IF_ICMPGE, label);

                // this part is the option of dumping individual field of "this"
                for (String s: fieldInfo) {
                    String[] temp = s.split(" ");
                    String fieldClassName = temp[0];
                    String fieldName = temp[1];
                    String fieldDescriptor = temp[2];

                    this.mv.visitFieldInsn(Opcodes.GETSTATIC,"inst/InstrumentationUtils","afterAllStates","Ljava/util/List;");
                    mv.visitVarInsn(Opcodes.ALOAD, 0);
                    System.err.println("fieldClassName: " + fieldClassName + " fieldName: " + fieldName + " fieldDescriptor: " + fieldDescriptor);
                    mv.visitFieldInsn(Opcodes.GETFIELD, fieldClassName, fieldName, fieldDescriptor);
                    String fieldType = fieldDescriptor;
                    System.err.println(fieldType);
                    if (AsmUtils.isPrimitive(fieldType)) {
                        this.mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                                "java/lang/" + AsmUtils.desToType(fieldType),
                                "valueOf",
                                "(" + fieldType + ")Ljava/lang/" + AsmUtils.desToType(fieldType) + ";",
                                false);
                    }

                    mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "add","(Ljava/lang/Object;)Z", true);
                    mv.visitInsn(Opcodes.POP);
                    this.addLocationAndType("this\n" + fieldDescriptor);
                }
                mv.visitLabel(label);


            }
        }
    }

    /**
     * dump each object and the corresponding variable in the object list
     * @param opcode
     */
    @Override
    public void visitInsn(int opcode) {
        if (this.isAfterAll && isReturn(opcode)) {

            mv.visitFieldInsn(Opcodes.GETSTATIC, "inst/InstrumentationUtils", "afterAllStates", "Ljava/util/List;");

            //dump the object array using XStream
            this.mv.visitLdcInsn("AfterAll");
            this.mv.visitLdcInsn("AfterAll");
            this.mv.visitLdcInsn("AfterAll");

            this.mv.visitMethodInsn(Opcodes.INVOKESTATIC, "inst/InstrumentationUtils",
                    "dumpObjectUsingXml", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", false);
        }
        super.visitInsn(opcode);
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        super.visitTypeInsn(opcode, type); // Keep the original ANEWARRAY

        if (opcode == Opcodes.ANEWARRAY || opcode == Opcodes.NEWARRAY) {
            // Load the 'afterAllStates' static field onto the stack
            mv.visitFieldInsn(Opcodes.GETSTATIC, "inst/InstrumentationUtils", "afterAllStates", "Ljava/util/List;");
            // Invoke the 'size' method of the List interface
            mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "size", "()I", true);
            // Push the constant 1050 onto the stack
            mv.visitLdcInsn(10050);
            // Compare the size of the list with 1050, jump if the condition is met
            Label label = new Label();
            mv.visitJumpInsn(Opcodes.IF_ICMPGE, label);


            visitInsn(Opcodes.DUP);
            mv.visitFieldInsn(Opcodes.GETSTATIC, "inst/InstrumentationUtils", "afterAllStates", "Ljava/util/List;");
            visitInsn(Opcodes.SWAP);
            mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "add","(Ljava/lang/Object;)Z", true);
            mv.visitInsn(Opcodes.POP);
            System.err.println(type);
            this.addLocationAndType("array\n" + "[" + type);

            // Label marking the end of the conditional block
            mv.visitLabel(label);
        }
    }

    /**
     * add return value from any method invocation to the object list
     * @param opcode
     * @param owner
     * @param name
     * @param descriptor
     * @param isInterface
     */

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);


        String returnType = AsmUtils.getReturnType(descriptor);
        if (owner.equals("se/michaelthelin/spotify/TestUtil$MockedHttpManager")) {
            return;
        }

        // 1. object instantiation
        if (name.equals("<init>")  && !methodName.equals("<init>") ) {//

            // Load the 'afterAllStates' static field onto the stack
            mv.visitFieldInsn(Opcodes.GETSTATIC, "inst/InstrumentationUtils", "afterAllStates", "Ljava/util/List;");
            // Invoke the 'size' method of the List interface
            mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "size", "()I", true);
            // Push the constant 1050 onto the stack
            mv.visitLdcInsn(10050);
            // Compare the size of the list with 1050, jump if the condition is met
            Label label = new Label();
            mv.visitJumpInsn(Opcodes.IF_ICMPGE, label);




            visitInsn(Opcodes.DUP);
            mv.visitFieldInsn(Opcodes.GETSTATIC, "inst/InstrumentationUtils", "afterAllStates", "Ljava/util/List;");
            visitInsn(Opcodes.SWAP);
            mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "add","(Ljava/lang/Object;)Z", true);
            mv.visitInsn(Opcodes.POP);
            this.addLocationAndType("init" + "\n" + "L" + owner + ";");


            // Label marking the end of the conditional block
            mv.visitLabel(label);

            return;
        }

        // 2. method invocation with return values

        if (!returnType.equals("V")) {

            // Load the 'afterAllStates' static field onto the stack
            mv.visitFieldInsn(Opcodes.GETSTATIC, "inst/InstrumentationUtils", "afterAllStates", "Ljava/util/List;");
            // Invoke the 'size' method of the List interface
            mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "size", "()I", true);
            // Push the constant 1050 onto the stack
            mv.visitLdcInsn(10050);
            // Compare the size of the list with 1050, jump if the condition is met
            Label label = new Label();
            mv.visitJumpInsn(Opcodes.IF_ICMPGE, label);

            System.err.println(owner + " " + returnType);
            // For Non-long/double type return value
            if (!returnType.equals("D") && !returnType.equals("J")) {
                visitInsn(Opcodes.DUP);
                mv.visitFieldInsn(Opcodes.GETSTATIC, "inst/InstrumentationUtils", "afterAllStates", "Ljava/util/List;");
                visitInsn(Opcodes.SWAP);
                //coxpy and put the return value/reference onto the operand stack
            } else {
                visitInsn(Opcodes.DUP2);
                this.mv.visitFieldInsn(Opcodes.GETSTATIC,"inst/InstrumentationUtils","afterAllStates","Ljava/util/List;");
                visitInsn(Opcodes.DUP_X2);
                visitInsn(Opcodes.POP);
            }
            if (AsmUtils.isPrimitive(returnType)) {
                this.mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                        "java/lang/" + AsmUtils.desToType(returnType),
                        "valueOf",
                        "(" + returnType + ")Ljava/lang/" + AsmUtils.desToType(returnType) + ";",
                        false);
            }

            mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "add","(Ljava/lang/Object;)Z", true);
            mv.visitInsn(Opcodes.POP);
            this.addLocationAndType("return\n" + owner + " " + descriptor.split("\\)")[1]);


            mv.visitLabel(label);
        }
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (descriptor.endsWith("/BeforeAll;")) {
            this.isBeforeAll = true;
        } else if (descriptor.endsWith("/AfterAll;")){
            this.isAfterAll = true;
        } else if(descriptor.endsWith("Test;")) {
            this.isTest = true;
        }
        return super.visitAnnotation(descriptor, visible);
    }

    public static Set<Integer> returnStm = new HashSet<Integer>();

    static {
        // initialze returnStm sets
        returnStm.add(Opcodes.IRETURN);
        returnStm.add(Opcodes.FRETURN);
        returnStm.add(Opcodes.ARETURN);
        returnStm.add(Opcodes.LRETURN);
        returnStm.add(Opcodes.RETURN);
        returnStm.add(Opcodes.DRETURN);
    }

    public static Boolean isReturn(int op) {
        if (returnStm.contains(op)) {
            return true;
        }
        return false;
    }

    private void addLocationAndType(String type) {
        String loc = this.className + " " + this.methodName + " " + this.curline + " " + type;
        mv.visitFieldInsn(Opcodes.GETSTATIC, "inst/InstrumentationUtils", "afterAllStatesInfo", "Ljava/util/List;");
        this.mv.visitLdcInsn(loc);
        mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "add","(Ljava/lang/Object;)Z", true);
        mv.visitInsn(Opcodes.POP);


    }


    @Override
    public void visitVarInsn(int opcode, int var) {

        if (opcode != Opcodes.LSTORE && opcode != Opcodes.DSTORE && opcode != Opcodes.ISTORE && opcode != Opcodes.ASTORE && opcode != Opcodes.FSTORE) {
            super.visitVarInsn(opcode, var);
            return;
        }

        LocalVariable loc = temp.get(0);
        temp.remove(0);
        if (loc == null) {
            // this is not a local variable
            super.visitVarInsn(opcode, var);
            return;
        }
        String target_desc = loc.desc;

        // Load the 'afterAllStates' static field onto the stack
        mv.visitFieldInsn(Opcodes.GETSTATIC, "inst/InstrumentationUtils", "afterAllStates", "Ljava/util/List;");
        // Invoke the 'size' method of the List interface
        mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "size", "()I", true);
        // Push the constant 1050 onto the stack
        mv.visitLdcInsn(10050);
        // Compare the size of the list with 1050, jump if the condition is met
        Label label = new Label();
        mv.visitJumpInsn(Opcodes.IF_ICMPGE, label);

        System.out.println("opcode: " + opcode + " var: " + var);

        // Swap the stack order to add to the List
        switch (opcode) {
            case Opcodes.LSTORE:
            case Opcodes.DSTORE:
                visitInsn(Opcodes.DUP2);
                // Load your static List field onto the stack
                mv.visitFieldInsn(Opcodes.GETSTATIC, "inst/InstrumentationUtils", "afterAllStates", "Ljava/util/List;");
                visitInsn(Opcodes.DUP_X2);
                visitInsn(Opcodes.POP);

                break;
            case Opcodes.ISTORE:
            case Opcodes.ASTORE:
            case Opcodes.FSTORE:
                visitInsn(Opcodes.DUP);
                mv.visitFieldInsn(Opcodes.GETSTATIC, "inst/InstrumentationUtils", "afterAllStates", "Ljava/util/List;");
                visitInsn(Opcodes.SWAP);
                break;
        }

        // Box the primitive types
        switch (opcode) {
            case Opcodes.ISTORE:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
                break;
            case Opcodes.FSTORE:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);
                break;
            case Opcodes.DSTORE:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
                break;
            case Opcodes.LSTORE:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
                break;
            // For ASTORE, it's already an Object, so no need to box.
        }

        // Call the List.add method to add the variable to the list
        mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z", true);

        // Pop the boolean result returned by List.add as we don't need it
        mv.visitInsn(Opcodes.POP);
        addLocationAndType("Local\n" + target_desc);

        mv.visitLabel(label);

        // Call the original store operation
        super.visitVarInsn(opcode, var);
    }



}


