package org.example;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AsmUtils {
    public static Set<Integer> returnStm = new HashSet<Integer>();

    public static Set<String> primitiveDes = new HashSet<>();

    public static boolean isPrimitive(String desc) {
        if (primitiveDes.contains(desc)) {
            return true;
        }
        return false;
    }

    public static String desToType(String des) {
        switch (des) {
            case "F":
                return "Float";
            case "J":
                return "Long";
            case "D":
                return "Double";
            case "I":
                return "Integer";
            case "B":
                return "Byte";
            case "C":
                return "Character";
            case "S":
                return "Short";
            case "Z":
                return "Boolean";
            default:
                throw new IllegalArgumentException("des should be primitive type, but it is: " + des);
        }
    }

    static {
        // initialze returnStm sets
        returnStm.add(Opcodes.IRETURN);
        returnStm.add(Opcodes.FRETURN);
        returnStm.add(Opcodes.ARETURN);
        returnStm.add(Opcodes.LRETURN);
        returnStm.add(Opcodes.RETURN);
        returnStm.add(Opcodes.DRETURN);
        returnStm.add(Opcodes.FRETURN);


        primitiveDes.add("J");
        primitiveDes.add("D");
        primitiveDes.add("I");
        primitiveDes.add("B");
        primitiveDes.add("C");
        primitiveDes.add("S");
        primitiveDes.add("Z");
        primitiveDes.add("F");
    }

    public static Boolean isReturn(int op) {
        if (returnStm.contains(op)) {
            return true;
        }
        return false;
    }

    public static List<String> getArgTypes(String methodDesc) {
        List<String> argList = new ArrayList<String>();
        for (Type t: Type.getArgumentTypes(methodDesc)) {
            argList.add(t.toString());
        }
        return argList;
    }

    public static boolean isStatic(int access) {
        return ((access & Opcodes.ACC_STATIC) != 0);
    }

    public static String getReturnType(String methodDesc) {
        return Type.getReturnType(methodDesc).toString();
    }

    public static boolean isJCL(String x) {
        if (x.startsWith("java/") || (x.startsWith("javax/"))) {
            return true;
        }
        return false;
    }

}
