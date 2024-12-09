package org.example;

import java.io.*;

public class Utils {

    public static void appendToFile(String filename, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(content);
            writer.newLine();  // Add a new line after the content.
        } catch (IOException e) {
            throw new  RuntimeException(e);
        }
    }

    public static void writeFailInfo(String filename, String content) {

        File f = new File(filename);
        try {
            f.createNewFile();
            appendToFile(filename,content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static Boolean isAssertionStatement(String owner,String methodName) {
        // owner.startsWith("org/assertj")
        final boolean b = methodName.startsWith("assert") | (methodName.startsWith("fail"));
        if (owner.startsWith("org/junit/jupiter/api") && b) {
            return true;
        }
        if (owner.startsWith("org/junit/Assert") && b) {
            return true;
        }
        if (owner.startsWith("junit/framework") && b) {
            return true;
        }
        return false;
    }

    public static Boolean isMyAssertionStatement(String owner,String methodName) {
        // owner.startsWith("org/assertj")
        if (owner.startsWith("ProxyAssertions/customized") && methodName.startsWith("runAssertion") ) {
            return true;
        }
        return false;
    }


    public static void appendLogAt(String filename, String[] lines){
        try (FileWriter f = new FileWriter(filename, true);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter p = new PrintWriter(b);)
        {
            for(String line: lines){
                p.println(line);
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
