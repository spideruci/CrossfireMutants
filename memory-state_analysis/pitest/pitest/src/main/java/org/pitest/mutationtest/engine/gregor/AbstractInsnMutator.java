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
package org.pitest.mutationtest.engine.gregor;

import java.util.Map;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.bytecode.ASMVersion;
import org.pitest.mutationtest.engine.MutationIdentifier;
import stateprobe.pitest.ProblemRecorder;

public abstract class AbstractInsnMutator extends MethodVisitor {

  private final MethodMutatorFactory factory;
  private final MutationContext      context;
  private final MethodInfo           methodInfo;

  public AbstractInsnMutator(final MethodMutatorFactory factory,
      final MethodInfo methodInfo, final MutationContext context,
      final MethodVisitor delegateMethodVisitor) {
    super(ASMVersion.ASM_VERSION, delegateMethodVisitor);
    this.factory = factory;
    this.methodInfo = methodInfo;
    this.context = context;
  }

  protected abstract Map<Integer, ZeroOperandMutation> getMutations();

  @Override
  public void visitInsn(final int opcode) {
    if (canMutate(opcode)) {
      createMutationForInsnOpcode(opcode);
    } else {
      this.mv.visitInsn(opcode);
    }
  }

  protected boolean canMutate(final int opcode) {
    return getMutations().containsKey(opcode);
  }

  private void createMutationForInsnOpcode(final int opcode) {
    final ZeroOperandMutation mutation = getMutations().get(opcode);

    final MutationIdentifier newId = this.context.registerMutation(
        this.factory, mutation.describe(opcode, this.methodInfo));

    if (this.context.shouldMutate(newId)) {
      //record the method desc for instrument code before return statement
      ProblemRecorder.clearFile("target/mutatedDesc.txt");
      ProblemRecorder.addOneLine(newId.getLocation().getClassName()
              + " " + newId.getLocation().getMethodName()
              + " " + newId.getLocation().getMethodDesc(), "target/mutatedDesc.txt");
      mutation.apply(opcode, this.mv);
    } else {
      applyUnmutatedInstruction(opcode);
    }
  }

  private void applyUnmutatedInstruction(final int opcode) {
    this.mv.visitInsn(opcode);
  }

  protected MethodInfo methodInfo() {
    return this.methodInfo;
  }

}
