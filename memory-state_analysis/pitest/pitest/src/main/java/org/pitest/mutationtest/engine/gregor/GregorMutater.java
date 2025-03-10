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

import static org.pitest.functional.prelude.Prelude.and;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.pitest.bytecode.FrameOptions;
import org.pitest.bytecode.NullVisitor;
import org.pitest.classinfo.ClassByteArraySource;
import org.pitest.classinfo.ClassName;
import org.pitest.classinfo.ComputeClassWriter;
import org.pitest.functional.FCollection;
import org.pitest.mutationtest.engine.Mutant;
import org.pitest.mutationtest.engine.Mutater;
import org.pitest.mutationtest.engine.MutationDetails;
import org.pitest.mutationtest.engine.MutationIdentifier;

public class GregorMutater implements Mutater {

  private final Map<String, String>       computeCache   = new HashMap<>();
  private final Predicate<MethodInfo>     filter;
  private final ClassByteArraySource      byteSource;
  private final List<MethodMutatorFactory> mutators;

  public GregorMutater(final ClassByteArraySource byteSource,
      final Predicate<MethodInfo> filter,
      final Collection<MethodMutatorFactory> mutators) {
    this.filter = filter;
    this.mutators = orderAndDeDuplicate(mutators);
    this.byteSource = byteSource;
  }

  @Override
  public List<MutationDetails> findMutations(
      final ClassName classToMutate) {

    final ClassContext context = new ClassContext();
    context.setTargetMutation(Optional.empty());
    Optional<byte[]> bytes = GregorMutater.this.byteSource.getBytes(
        classToMutate.asInternalName());
    
    return bytes.map(findMutations(context))
        .orElse(Collections.emptyList());

  }

  private Function<byte[], List<MutationDetails>> findMutations(
      final ClassContext context) {
    return bytes -> findMutationsForBytes(context, bytes);
  }

  private List<MutationDetails> findMutationsForBytes(
      final ClassContext context, final byte[] classToMutate) {

    final ClassReader first = new ClassReader(classToMutate);
    final NullVisitor nv = new NullVisitor();
    final MutatingClassVisitor mca = new MutatingClassVisitor(nv, context,
        filterMethods(), this.mutators);

    first.accept(mca, ClassReader.EXPAND_FRAMES);

    return new ArrayList<>(context.getCollectedMutations());
  }

  @Override
  public Mutant getMutation(final MutationIdentifier id) {


    final ClassContext context = new ClassContext();
    context.setTargetMutation(Optional.ofNullable(id));

    final Optional<byte[]> bytes = this.byteSource.getBytes(id.getClassName()
        .asJavaName());
    final ClassReader reader = new ClassReader(bytes.get());
    final ClassWriter w = new ComputeClassWriter(this.byteSource,
        this.computeCache, FrameOptions.pickFlags(bytes.get()));
    final MutatingClassVisitor mca = new MutatingClassVisitor(w, context,
        filterMethods(), FCollection.filter(this.mutators,
            isMutatorFor(id)));
    // first time instrumentation for mutation from PIT and probes that indicate the execution of mutation
    reader.accept(mca, ClassReader.EXPAND_FRAMES);
    final List<MutationDetails> details = context.getMutationDetails(context
        .getTargetMutation().get());



    //second time instrumentation for MR, place probes through onMethodExit
//    ClassReader cr2 = new ClassReader(w.toByteArray());
//    ClassWriter cw2 = new ClassWriter(cr2, ClassWriter.COMPUTE_FRAMES);
//    ClassVisitor stateRecorder = new MRMethodEndClassVisitor(cw2);
//    cr2.accept(stateRecorder, ClassReader.EXPAND_FRAMES);
//    System.err.println(context.getClassInfo().getName());
//    //third time instrumentation for MR, surround the mutated method with a try-catch block to see unexpected method
//    ClassReader cr3 = new ClassReader(cw2.toByteArray());
//    ClassWriter cw3 = new MyClassWriter(cr3, ClassWriter.COMPUTE_FRAMES);
////    ClassWriter cw3 = new ClassWriter(cr3, ClassWriter.COMPUTE_FRAMES);
//    ClassVisitor tryInstrumenter = new MRTryCatchClassVisitor(cw3);
//    cr3.accept(tryInstrumenter, ClassReader.EXPAND_FRAMES);

    // for debugging
//    try {
//      PrintStream byteStream = new PrintStream("target/test-classes/" + System.nanoTime() + ".class");
//      byteStream.write(cw3.toByteArray());
//      byteStream.close();
//    } catch (Exception e) {
//      e.printStackTrace();
//      System.err.println("soemthign wrong");
//    }

    return new Mutant(details.get(0), bytes.get(), w.toByteArray());
  }

  private static Predicate<MethodMutatorFactory> isMutatorFor(
      final MutationIdentifier id) {
    return a -> id.getMutator().equals(a.getGloballyUniqueId());
  }

  private Predicate<MethodInfo> filterMethods() {
    return and(this.filter, filterSyntheticMethods());
  }

  private static Predicate<MethodInfo> filterSyntheticMethods() {
    return a -> !a.isSynthetic() || a.getName().startsWith("lambda$");
  }

  private List<MethodMutatorFactory> orderAndDeDuplicate(Collection<MethodMutatorFactory> mutators) {
    // deduplication is based on object identity, so dubious that this adds any value
    // however left in place for now to replicate HashSet behaviour
    return mutators.stream()
            .distinct()
            .sorted(Comparator.comparing(MethodMutatorFactory::getGloballyUniqueId))
            .collect(Collectors.toList());
  }

}
