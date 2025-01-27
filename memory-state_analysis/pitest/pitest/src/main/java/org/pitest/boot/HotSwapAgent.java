/*
 * Copyright 2010 Henry Coles
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package org.pitest.boot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;


public class HotSwapAgent {

  private static Instrumentation instrumentation;

  public static synchronized void checkExistsOrCreate(String filePath) {
    File targetFile = new File(filePath);
    if (!targetFile.exists()) {
      try {
        targetFile.createNewFile();
      } catch (IOException e) {
        System.err.println("something went wrong in checkExistsOrCreate In problemRecorder");
        throw new RuntimeException(e);
      }
    }
  }

  public static synchronized void clearFile(String filePath) {
    if (filePath.equals("target/MRs.txt")) {
      System.err.println("clear target/MRs.txt");
    }


    checkExistsOrCreate(filePath);
    try {
      // Create a new FileWriter object that points to the text file
      FileWriter writer = new FileWriter(filePath);

      // Overwrite the contents of the text file with an empty string
      writer.write("");

      // Close the file and save the changes
      writer.close();
    } catch (IOException e) {
      System.err.println("something went wrong in clearfile in ProblemRecorder");
      // Handle the exception
    }
  }

  public static synchronized void addOneLine(String info, String filePath) {
    File targetFile = new File(filePath);
    // Check if the file exists, and create it if it doesn't
    checkExistsOrCreate(filePath);

    // Append a line to the end of the file
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFile, true))) {
      bw.write(info);
      bw.newLine();
    } catch (IOException e) {
      System.err.println("something went wrong in addOneLine in ProblemRecorder");
      e.printStackTrace();
    }
  }
  public static void premain(final String agentArguments, // NO_UCD
      final Instrumentation inst) {
    clearFile("target/GlobalStates.txt");
    addOneLine("2", "target/GlobalStates.txt");
    System.err.println("finish mutation state");

    System.err.println("set null state");
    System.out.println("Installing PIT agent");
    instrumentation = inst;
  }

  public static void addTransformer(final ClassFileTransformer transformer) {
    instrumentation.addTransformer(transformer);
  }

  public static void agentmain(final String agentArguments, // NO_UCD
      final Instrumentation inst) {
    instrumentation = inst;
  }

  public static boolean hotSwap(final Class<?> mutateMe, final byte[] bytes) { // NO_UCD

    final ClassDefinition[] definitions = { new ClassDefinition(mutateMe, bytes) };

    try {
      instrumentation.redefineClasses(definitions);
      return true;
    } catch (final ClassNotFoundException | UnmodifiableClassException | VerifyError | InternalError e) {
      e.printStackTrace();
      // swallow
    }
    return false;
  }

}
