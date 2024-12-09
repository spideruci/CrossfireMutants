package org.example;

import org.objectweb.asm.ClassWriter;

public class MyClassWriter extends ClassWriter {
    public MyClassWriter(int flags) {
        super(flags);
    }

    @Override
    protected String getCommonSuperClass(String type1, String type2) {
        return super.getCommonSuperClass(type1, type2);
    }
}
