/*
 * Copyright 2011 Henry Coles
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
package org.pitest.maven;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.pitest.classinfo.ClassName;
import org.pitest.classpath.DirectoryClassPathRoot;
import org.pitest.functional.FCollection;
import org.pitest.mutationtest.config.ReportOptions;
import org.pitest.testapi.TestGroupConfig;
import org.pitest.util.Glob;
import org.pitest.util.Verbosity;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MojoToReportOptionsConverter {

  private final AbstractPitMojo                 mojo;
  private final Predicate<Artifact>     dependencyFilter;
  private final Log                     log;
  private final SurefireConfigConverter surefireConverter;

  public MojoToReportOptionsConverter(final AbstractPitMojo mojo,
      SurefireConfigConverter surefireConverter,
      Predicate<Artifact> dependencyFilter) {
    this.mojo = mojo;
    this.dependencyFilter = dependencyFilter;
    this.log = mojo.getLog();
    this.surefireConverter = surefireConverter;
  }

  public ReportOptions convert() {

    final List<String> classPath = new ArrayList<>();

    try {
      classPath.addAll(this.mojo.getProject().getTestClasspathElements());
    } catch (final DependencyResolutionRequiredException e1) {
      this.log.info(e1);
    }

    addOwnDependenciesToClassPath(classPath);

    classPath.addAll(this.mojo.getAdditionalClasspathElements());

    for (Object artifact : this.mojo.getProject().getArtifacts()) {
      final Artifact dependency = (Artifact) artifact;

      if (this.mojo.getClasspathDependencyExcludes().contains(
          dependency.getGroupId() + ":" + dependency.getArtifactId())) {
        classPath.remove(dependency.getFile().getPath());
      }
    }

    ReportOptions option = parseReportOptions(classPath);
    return updateFromSurefire(option);

  }

  private ReportOptions parseReportOptions(final List<String> classPath) {
    final ReportOptions data = new ReportOptions();

    if (this.mojo.getProject().getBuild() != null) {
      this.log.info("Mutating from "
          + this.mojo.getProject().getBuild().getOutputDirectory());
      data.setCodePaths(Collections.singleton(this.mojo.getProject().getBuild()
          .getOutputDirectory()));
    }

    data.setUseClasspathJar(this.mojo.isUseClasspathJar());
    data.setClassPathElements(classPath);

    data.setFailWhenNoMutations(shouldFailWhenNoMutations());

    data.setTargetClasses(determineTargetClasses());
    data.setTargetTests(determineTargetTests());

    data.setExcludedMethods(this.mojo
        .getExcludedMethods());
    data.setExcludedClasses(this.mojo.getExcludedClasses());
    data.setExcludedTestClasses(globStringsToPredicates(this.mojo
        .getExcludedTestClasses()));
    data.setNumberOfThreads(this.mojo.getThreads());
    data.setExcludedRunners(this.mojo.getExcludedRunners());

    data.setReportDir(this.mojo.getReportsDirectory().getAbsolutePath());
    configureVerbosity(data);
    if (this.mojo.getJvmArgs() != null) {
      data.addChildJVMArgs(this.mojo.getJvmArgs());
    }
    if (this.mojo.getArgLine() != null) {
      data.setArgLine(this.mojo.getArgLine());
    }

    data.setMutators(determineMutators());
    data.setFeatures(determineFeatures());
    data.setTimeoutConstant(this.mojo.getTimeoutConstant());
    data.setTimeoutFactor(this.mojo.getTimeoutFactor());
    if (hasValue(this.mojo.getAvoidCallsTo())) {
      data.setLoggingClasses(this.mojo.getAvoidCallsTo());
    }

    data.setProjectDomain(this.mojo.getProjectDomain());
    System.err.println("You made it! I received the project domain argument in MojoToReportOptionsConverter.java: " + data.getProjectDomain());

    final List<String> sourceRoots = new ArrayList<>();
    sourceRoots.addAll(this.mojo.getProject().getCompileSourceRoots());
    sourceRoots.addAll(this.mojo.getProject().getTestCompileSourceRoots());

    data.setSourceDirs(stringsToPaths(sourceRoots));

    data.addOutputFormats(determineOutputFormats());

    setTestGroups(data);

    data.setFullMutationMatrix(this.mojo.isFullMutationMatrix());

    data.setMutationUnitSize(this.mojo.getMutationUnitSize());
    data.setShouldCreateTimestampedReports(this.mojo.isTimestampedReports());
    data.setDetectInlinedCode(this.mojo.isDetectInlinedCode());

    determineHistory(data);
    
    data.setExportLineCoverage(this.mojo.isExportLineCoverage());
    data.setMutationEngine(this.mojo.getMutationEngine());
    data.setJavaExecutable(this.mojo.getJavaExecutable());
    data.setFreeFormProperties(createPluginProperties());
    data.setIncludedTestMethods(this.mojo.getIncludedTestMethods());

    data.setSkipFailingTests(this.mojo.skipFailingTests());

    data.setInputEncoding(this.mojo.getSourceEncoding());
    data.setOutputEncoding(this.mojo.getOutputEncoding());

    if (this.mojo.getProjectBase() != null) {
      data.setProjectBase(FileSystems.getDefault().getPath(this.mojo.getProjectBase()));
    }

    checkForObsoleteOptions(this.mojo);

    return data;
  }

  private void configureVerbosity(ReportOptions data) {
    if (this.mojo.isVerbose()) {
      data.setVerbosity(Verbosity.VERBOSE);
    } else {
      Verbosity v = Verbosity.fromString(mojo.getVerbosity());
      data.setVerbosity(v);
    }

  }

  private void checkForObsoleteOptions(AbstractPitMojo mojo) {
    if (mojo.getMaxMutationsPerClass() > 0) {
      throw new IllegalArgumentException("The max mutations per class argument is no longer supported, "
              + "use features=+CLASSLIMIT(limit[" + mojo.getMaxMutationsPerClass() + "]) instead");
    }
  }

  private void determineHistory(final ReportOptions data) {
    if (this.mojo.useHistory()) {
      useHistoryFileInTempDir(data);
    } else {
      data.setHistoryInputLocation(this.mojo.getHistoryInputFile());
      data.setHistoryOutputLocation(this.mojo.getHistoryOutputFile());
    }
  }

  private void useHistoryFileInTempDir(final ReportOptions data) {
    String tempDir = System.getProperty("java.io.tmpdir");
    MavenProject project = this.mojo.getProject();
    String name = project.getGroupId() + "."
        + project.getArtifactId() + "."
        + project.getVersion() + "_pitest_history.bin";
    File historyFile = new File(tempDir, name);
    log.info("Will read and write history at " + historyFile);
    if (this.mojo.getHistoryInputFile() == null) {
      data.setHistoryInputLocation(historyFile);
    }
    if (this.mojo.getHistoryOutputFile() == null) {
      data.setHistoryOutputLocation(historyFile);
    }
  }
  
  private ReportOptions updateFromSurefire(ReportOptions option) {
    Collection<Plugin> plugins = lookupPlugin("org.apache.maven.plugins:maven-surefire-plugin");
    if (!this.mojo.isParseSurefireConfig()) {
      return option;
    } else if (plugins.isEmpty()) {
      this.log.warn("Could not find surefire configuration in pom");
      return option;
    }

    Plugin surefire = plugins.iterator().next();
    if (surefire != null) {
      return this.surefireConverter.update(option,
          (Xpp3Dom) surefire.getConfiguration());
    } else {
      return option;
    }

  }

  private Collection<Plugin> lookupPlugin(String key) {
    List<Plugin> plugins = this.mojo.getProject().getBuildPlugins();
    return FCollection.filter(plugins, hasKey(key));
  }

  private static Predicate<Plugin> hasKey(final String key) {
    return a -> a.getKey().equals(key);
  }

  private boolean shouldFailWhenNoMutations() {
    return this.mojo.isFailWhenNoMutations();
  }

  private void setTestGroups(final ReportOptions data) {
    final TestGroupConfig conf = new TestGroupConfig(
        this.mojo.getExcludedGroups(), this.mojo.getIncludedGroups());
    data.setGroupConfig(conf);
  }

  private void addOwnDependenciesToClassPath(final List<String> classPath) {
    for (final Artifact dependency : filteredDependencies()) {
      this.log.info("Adding " + dependency.getGroupId() + ":"
          + dependency.getArtifactId() + " to SUT classpath");
      classPath.add(dependency.getFile().getAbsolutePath());
    }
  }

  private Collection<Predicate<String>> globStringsToPredicates(
      final List<String> excludedMethods) {
    return FCollection.map(excludedMethods, Glob.toGlobPredicate());
  }

  private Collection<Predicate<String>> determineTargetTests() {
    return FCollection.map(useConfiguredTargetTestsOrFindOccupiedPackages(this.mojo.getTargetTests()), Glob.toGlobPredicate());
  }

  private Collection<String> useConfiguredTargetTestsOrFindOccupiedPackages(
      final Collection<String> filters) {
    if (!hasValue(filters)) {
      this.mojo.getLog().info("Defaulting target tests to match packages in test build directory");
      return findOccupiedTestPackages();
    } else {
      return filters;
    }
  }

  private Collection<String> findOccupiedTestPackages() {
    String outputDirName = this.mojo.getProject().getBuild()
        .getTestOutputDirectory();
    if (outputDirName != null) {
        File outputDir = new File(outputDirName);
        return findOccupiedPackagesIn(outputDir);
    } else {
        return Collections.emptyList();
    }
  }

  private Collection<Artifact> filteredDependencies() {
    return FCollection.filter(this.mojo.getPluginArtifactMap().values(),
        this.dependencyFilter);
  }

  private Collection<String> determineMutators() {
    if (this.mojo.getMutators() != null) {
      return this.mojo.getMutators();
    } else {
      return Collections.emptyList();
    }
  }

  private Collection<String> determineFeatures() {
      if (this.mojo.getFeatures() != null) {
        return this.mojo.getFeatures();
      } else {
        return Collections.emptyList();
      }
  }  
  
  private Collection<String> determineTargetClasses() {
    return useConfiguredTargetClassesOrFindOccupiedPackages(this.mojo.getTargetClasses());
  }

  private Collection<String> useConfiguredTargetClassesOrFindOccupiedPackages(
      final Collection<String> filters) {
    if (!hasValue(filters)) {
      this.mojo.getLog().info("Defaulting target classes to match packages in build directory");
      return findOccupiedPackages();
    } else {
      return filters;
    }
  }

  private Collection<String> findOccupiedPackages() {
    String outputDirName = this.mojo.getProject().getBuild()
        .getOutputDirectory();
    File outputDir = new File(outputDirName);
    return findOccupiedPackagesIn(outputDir);
  }
  
  public static Collection<String> findOccupiedPackagesIn(File dir) {
    if (dir.exists()) {
      DirectoryClassPathRoot root = new DirectoryClassPathRoot(dir);
      Set<String> occupiedPackages = new HashSet<>();
      FCollection.mapTo(root.classNames(), classToPackageGlob(),
          occupiedPackages);
      return occupiedPackages;
    }
    return Collections.emptyList();
  }
  
  private static Function<String,String> classToPackageGlob() {
    return a -> ClassName.fromString(a).getPackage().asJavaName() + ".*";
  }

  private Collection<Path> stringsToPaths(final List<String> sourceRoots) {
    return sourceRoots.stream()
                    .map(Paths::get)
                            .collect(Collectors.toList());
  }

  private Collection<String> determineOutputFormats() {
    if (hasValue(this.mojo.getOutputFormats())) {
      return this.mojo.getOutputFormats();
    } else {
      return Arrays.asList("HTML");
    }
  }

  private boolean hasValue(final Collection<?> collection) {
    return (collection != null) && !collection.isEmpty();
  }

  private Properties createPluginProperties() {
    Properties p = new Properties();
    if (this.mojo.getPluginProperties() != null) {
      p.putAll(this.mojo.getPluginProperties());
    }
    return p;
  }

}
