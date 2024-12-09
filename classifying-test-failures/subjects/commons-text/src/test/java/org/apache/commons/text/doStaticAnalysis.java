package org.apache.commons.text;

import soot.*;
import soot.options.Options;

import java.util.Collections;

public class doStaticAnalysis {
    public static void main(String[] args) {
        // Set Soot's classpath
        analyseCH("target");
    }

    public static void analyseCH(String path) {
        String cp = "target/classes";

        // Initialize Soot
        G.reset();
        Options.v().set_whole_program(true);
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_prepend_classpath(true);
        Options.v().set_process_dir(Collections.singletonList(cp));
        Options.v().set_soot_classpath(cp);
        Scene.v().loadNecessaryClasses();

        // Analyze class hierarchy
        for (SootClass sootClass : Scene.v().getApplicationClasses()) {
            if (sootClass.hasSuperclass() && !sootClass.getSuperclass().getName().equals("java.lang.Object")) {
                System.out.println(sootClass.getName() + " extends " + sootClass.getSuperclass().getName());
            }
        }
    }
}
