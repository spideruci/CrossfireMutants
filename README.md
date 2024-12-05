# Introduction
This artifact package accompanies our submission to ICSE 2025, titled "Leveraging Propagated Infection to Crossfire Mutants."
We zipped modified PIT and subjects, and split propagation data into small pieces because they contain hundreds of source-code files or large single files, which break Github's rules or take a tremendous amount of time for Anonymous Github to process.
They can be downloaded, extracted, and viewed on local machines, though they cannot be viewed on web pages.

This package is designed to provide the implementation source code, and the dataset presented in our paper, involving two separate experiments of classifying failing test runs and performing propagation analysis on passing test runs.
Below is an overview of the contents of this package:

- We include supplementary figures for RQ1 which include heatmaps for all subject projects. 
- We include a simple motivating real-life example as a result of our analysis to demonstrate the interesting cross-firing attribute of our approach.
- we include an extreme real-life example from our analysis as in the Spotify-Web-API project, where an assertion-augmented test case kills **57** alive mutants all at once!
- We include both the implementation and the dataset for identifying assertion-mutant killing pairs from failing test runs on mutants. This component categorizes test failures—as revealed propagation—into specific assertion failures or non-assertion failures.
- We provide the implementation and dataset for conducting a fine-grained propagation analysis for passing test runs, where we investigate the popularity and magnitude of propagation, the comprehensive alive mutant killing opportunities, and mutant crossfire techniques.
  Specifically, we modified [PITest](https://pitest.org/) (a bytecode mutation testing tool for Java) to facilitate our experiments.
# Table of Contents
- [Supplementary Pictures](#supplementary-pictures)
- [Motivating Examples](#motivating-examples)
    - [A simple real-life example in Commons-text](#a-simple-real-life-example-in-commons-text)
    - [An extreme real-life example in Spotify-Web-API](#an-extreme-real-life-example-in-spotify-web-api)
- [Classifying test failures](#classifying-test-failures)
- [Propagation analysis for passing test runs](#fine-grained-propagation-analysis-for-passing-test-runs)
- [Crossfire Alive Mutants](#crossfire)

# Supplementary Pictures
In answering RQ1, we show the heatmaps for 6 subject projects, each showing the mutant killing capability of individual tests and assertions; and here we provide the supplementary high-quality figures for all subject projects.
The figures are stored in the `supplementary_pics` directory.
Specifically, individual figures are stored under `supplementary_pics/individual_pics` directory, and the combined figure is stored under `supplementary_pics/crossfire.pdf`.
For individual figures, the figures demonstrating each test's capability have names ending with "_all", while the figures demonstrating each assertion's capability have names ending with "_assertion".

We demonstrate the aggregated 10 pairs heatmaps for all subject projects here:
[heatmaps](supplementary_pics/crossfire.pdf).
For lossless displays of these figures, please use pdf reading software to open those figures.

# Motivating Examples

## A simple real life example in Commons-text
We provide one simple motivating example in this section to demonstrate the interesting crossfiring attribute of our approach.
The test case is in `/commons-text/src/test/java/org/apache/commons/text/ExtendedMessageFormatTest.java` for commons-text 1.10.0.

In the original test case, the test cases check if an IllegalArgumentException is thrown when creating an ExtendedMessageFormat object with an invalid format string.
This test case is able to kill some mutants, but it passes on three different alive mutants, with deviated exceptional messages.
```agsl
    Original test case:
    @Test
    public void testFailsToCreateExtendedMessageFormatTakingTwoArgumentsThrowsIllegalArgumentExceptionOne() {
        assertThatIllegalArgumentException().isThrownBy(() -> new ExtendedMessageFormat("agdXdkR;T1{9 ^,LzXf?", new HashMap<String, FormatFactory>()));
    }
```
****
If we augment this test case with one single assertion checking the exception message, then three alive mutants will be killed all at once!
```agsl
    Assertion augmented test case:
    @Test
    public void testFailsToCreateExtendedMessageFormatTakingTwoArgumentsThrowsIllegalArgumentExceptionOne() {
        assertThatIllegalArgumentException().isThrownBy(() -> new ExtendedMessageFormat("agdXdkR;T1{9 ^,LzXf?", new HashMap<String, FormatFactory>()))
                // new assertion added below
                // kills three different alive mutants all at once!
                .withMessage("Invalid format argument index at position 11: 9 ^ ");
    }
```

### Surviving Mutant Demographics
These three surviving mutants vary in locations, mutation operators, and even in the deviated exceptional messages. Below are their demographics:

Alive Mutant 1:
- mutator: VoidMethodCallMutator
- Location: ExtendedMessageFormat.java, line 473
- Description: removed call to org/apache/commons/text/ExtendedMessageFormat::seekNonWs

Alive Mutant 2:
- mutator: NegateConditionalsMutator
- Location: ExtendedMessageFormat.java, line 491
- Description: negated conditional

Alive Mutant 3:
- mutator: MathMutator
- Location: ExtendedMessageFormat.java line 511
- Description: Replaced integer addition with subtraction

## An extreme real-life example in Spotify-Web-API

We also include an additional interesting motivating example in the `spotify-web-api` project, where one single assertion-augmented test case kills **57** alive mutants all at once through our analysis.
The new test case is reconstructed through `shouldReturnDefault_sync()`.
We provide the information on each added assertion's mutant kills (mutant IDs) in the code.
Note that it does not necessarily mean all these assertions should be organized into one single augmented test case, however, assertions can be derived based on one existing test case and each of them may kill and crossfire mutants.
The test case is located at `/spotify-web-api/src/test/java/se/michaelthelin/spotify/KillMutants.java`.

# Classifying test failures
This component classifies test failures (propagation being revealed) into specific assertion failures or non-assertion failures.
We provide the implementation of source code under the directory `classifying-test-failures`.

Specifically, this experiment requires modifying PITest to collect information on test failure causes, i.e., assertion failures and non-assertion failures.
We correlate each test with all assertions running on the original program, and we manually attribute those `fail` assertions to specific test cases as they will only be executed when the test fails.

The directory contains the following:
- `test` directory: the modified PITest source code.
- `data` directory: the collected data of assertion failures (locate to the specific assertion in test code) and non-assertion failures.
- `subjects`: the prepared subject projects ready for the experiment.

Specifically, the fields of the CSV dataset are explained below:

| Field               | Description                                                                                                                  |
|---------------------|------------------------------------------------------------------------------------------------------------------------------|
| mutation_id         | mutation id (hashed)                                                                                                         |
| mutated_file        | the file that contains the mutation                                                                                          |
| mutated_line_number | the line that contains the mutation                                                                                          |
| mutation_state      | killed or survived                                                                                                           |
| mutator             | mutation operator                                                                                                            |
| test_id             | the name of the test case                                                                                                    |
| test_state          | pass/fail                                                                                                                    |
| first_fail_oracle   | the location of the assertion                                                                                                |


## Run the experiment

Requirements: 1) Maven 2) PIT installed (see instruction below) 3) JDK 8/11

To facilitate replicating the experiment, we provide configured subject projects under the subject directory, where we have prepared the jar file for instrumenting the test classes, configured PIT, and transformed test suite introduced in the paper for the experiment.
First, install PITest (`classifying-test-failures/pitest`), run:
```
mvn clean install -Dmaven.test.skip
```

Then open the terminal at the subject project's target module's path and run mutation analysis, type:

```
mvn clean compile test-compile
java -jar "p.jar" target/test-classes
mvn jacoco:instrument "-Dmaven.main.skip" pitest:mutationCoverage >info.txt 2>result.txt
```
The produced CSV file is stored at the path `target/csvData.csv`.


# Fine-grained propagation analysis for passing test runs
This component detects and analyzes propagation from passing test runs.
To conduct the experiment, we modified the source code of PITest again and instrumented the project test code to collect the final program state information.
We provide the implementation of source code and dataset under the directory `fine-grained-propagation-analysis`.

The directory contains the following:
- `pitest` directory: the modified PITest source code.
- `TestIntrumentor` directory: the project to instrument the test code.
- `subjects`: the processed subject projects we used in the experiment.
- `data` directory: the collected data of propagation information
- `crossfire` directory: the scripts demonstrating the performance of the greedy-mutant crossfire algorithms presented in the paper.

## extracting assertion candidates' access paths


## Data Structure Description

For each of the projects, the propagation data are stored in serialized Python objects, which are easy to query using Python.
Specifically, each subject project contains the following pickle file, including `MemoryData`, `AccessibilityData`, `NMR_full_blacklist`, and `NMR_itemized_blacklist`.
For storage reasons, data from Jfreechart and Jline-reader are compressed into separate files (ending with .gz).
We explain the data structure of all of the data below:

### MemoryData
`MemoryData` is a dictionary that contains the overall memory state of the program at the end of each test case execution.
The dictionary is structured as follows:

```plaintext
dict
 |
 +-- mutation_id (String Key)
      |
      +-- {Nested Dict for a mutant}
           |
           +-- "mutator" (String Key)
           |    |
           |    +-- mutation operator      
           |
           +-- "mutation_status" (String Key)
           |    |
           |    +-- KILLED/SURVIVED
           |          
           +-- "tests" (String Key)
                |
                +-- [List of Test Execution information for the mutant]
                     |
                     +-- 0: {Nested Dict for a test case}
                     |    |
                     |    +-- "test_state" (String Key)
                     |    |    |
                     |    |    +-- PASS/FAIL
                     |    +-- "test_name" (String Key)
                     |    |    |      
                     |    |    +-- Test Name
                     |    +-- "diffs" [Nested List of Memory Diff]
                     |         |
                     |         +-- 0: [root_name, depth (starting from 0), node_id, attributable_parent, diff_type]
                     |         +-- 1: ...
                     |
                     +-- 1: ...

```

### NMR_full_blacklist
`NMR_full_blacklist` is a set of node ids that are blacklisted (non-deterministic) from the memory analysis per test case.

```plaintext
dict
|
+-- test_id (String Key)
        |
        +-- {Set of blacklisted node ids}
```

### AccessibilityData
`AccessibilityData` is also a dictionary that contains per variable-based state diff information.
It contains the way to access polluted content during each test case execution, including all possible variables that can access the polluted content.
The dictionary is structured as follows:

```plaintext
dict
 |
 +-- mutation_id (String Key)
      |
      +-- {Nested Dict for a mutant}
           |
           +-- "mutator" (String Key)
           |    |
           |    +-- mutation operator      
           |
           +-- "mutation_status" (String Key)
           |    |
           |    +-- KILLED/SURVIVED
           |          
           +-- "tests" (String Key)
                |
                +-- [List of Test Execution information for the mutant]
                     |
                     +-- 0: {Nested Dict for a test case}
                     |    |
                     |    +-- "test_state" (String Key)
                     |    |    |
                     |    |    +-- PASS/FAIL
                     |    +-- "test_name" (String Key)
                     |    |    |      
                     |    |    +-- Test Name
                     |    +-- "diffs" {Nested Dict of Per-variable diff}
                     |         |
                     |         +-- "variable_id" (Integer key)
                     |              |
                     |              +-- [Per Variable Memory Diff meta information]
                     |                   |
                     |                   +-- 0: [List of detailed state diff information]
                     |                           |
                     |                           +-- 0: [root_name, depth (starting from 0), node_id, attributable_parent, diff_type]
                     |                           +-- 1: [variable's hash in mutated run, variable's hash in non-mutated run]
                     +-- 1: ...
```

### NMR_itemized_blacklist
`NMR_itemized_blacklist` contains blacklist node_ids regarding each variable-based state diff information per test case.

```plaintext
dict
|
+-- test_id (String Key)
        |
        +-- {Nested dict of per-variable blacklisted node ids}
             |
             +-- "variable_id" (Integer Key)
                  |
                  +-- {Set of blacklisted node ids}
```

## Run the experiment

Requirements: 1) Maven 2) PITest installed (see instruction below) 3) JDK 8/11

To facilitate replicating the experiment, we provide configured subject projects under the subject directory, where we have prepared the jar file for instrumenting the test classes, configured PITest, and transformed the test suite the way introduced in the paper for the experiment.
To run fine-grained propagation analysis for passing test runs, first install PIT (`fine-grained-propagation-analysis/pitest`), run:
```
mvn clean install -Dmaven.test.skip
```

Then open the terminal at the subject project's target module's path and run mutation analysis, type:

```
mvn clean compile test-compile
java -jar "p.jar" target/test-classes
mvn "-Dmaven.main.skip" pitest:mutationCoverage >info.txt 2>result.txt
```
The raw data related to per-mutant final program states are stored under `target/everything` directory for analysis.


# Crossfire
The `crossfire` directory contains the scripts demonstrating the performance of the greedy-mutant crossfire algorithms presented in the paper.
