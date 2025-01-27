# Purpose
This package is designed to provide the implementation source code, and the dataset presented in our paper (**Leveraging Propagated Infection to Crossfire Mutants**), involving two separate experiments of classifying failing test runs and performing propagation analysis on passing test runs.
Below is an overview of the contents of this package:

- We include both the implementation and the dataset for identifying assertion-mutant killing pairs from failing test runs on mutants. This component categorizes test failures—as revealed propagation—into specific assertion failures or non-assertion failures.
- We provide the implementation and dataset for conducting a fine-grained propagation analysis for passing test runs, where we investigate the popularity and magnitude of propagation, the comprehensive alive mutant killing opportunities, and mutant crossfire techniques.
  Specifically, we modified [PITest](https://pitest.org/) (a bytecode mutation testing tool for Java) to facilitate our experiments.
- We include supplementary figures for RQ1 which include heatmaps for all subject projects.
- We include a simple motivating real-life example as a result of our analysis to demonstrate the interesting cross-firing attribute of our approach.
- we include an extreme real-life example from our analysis as in the Spotify-Web-API project, where an assertion-augmented test case kills **57** alive mutants all at once!

# Setup

Running the complete analysis for each subject project can be time-consuming, ranging from approximately 2 hours to several weeks (as noted on page 6 of the paper). 
Additionally, the full analysis requires substantial disk space (up to 1TB). To mitigate these challenges for demonstration purposes, we provide Docker images pre-configured to run the analysis on a limited subset of mutants. 
These Docker containers automate the steps of our analysis.
For those interested in replicating our results in full, we have also made the source code, scripts, and build configurations available. 
However, please note that reproducing all experimental data may require several weeks of computation and over 1TB of disk storage.

## Requirements for running the Docker container:

**Hardware**: At least 10GB of free disk space for the Docker container. We provide Docker images compatible with both AMD and ARM-based architectures (e.g., MacBook with an M1 chip).

**Software**: Docker and Python 3.


# Usage
Our analysis consists of two separate experiments, one corresponding to RQ1 and the other to RQ2-4.
The first experiment keeps track of the causes of mutant kills (assertion failures or non-assertion failures) and which assertion kills which mutants. 

## Classifying test failures
This component classifies test failures (propagation being revealed) into specific assertion failures or non-assertion failures.
We provide the implementation of source code under the directory `classifying-test-failures`.

Specifically, this experiment requires modifying PITest to collect information on test failure causes and performing instrumentation on test code.
The `pitest` and `instrumentation` folder contains the implementation of the modified PITest and code used for instrumentation, respectively.
Moreover, `subjects` contains the processed subject projects we used in the experiment with assembled jar file attached for instrumentation.
The `data` directory contains the raw data of assertion-mutant killing pairs, answering RQ1.

Specifically, the important columns of the CSV dataset are explained below:

| Field               | Description                                                                                                |
|---------------------|------------------------------------------------------------------------------------------------------------|
| mutation_id         | mutation id (hashed)                                                                                       |
| mutated_file        | the file that contains the mutation                                                                        |
| mutated_line_number | the line that contains the mutation                                                                        |
| mutation_state      | killed or survived                                                                                         |
| mutator             | mutation operator                                                                                          |
| test_id             | the name of the test case                                                                                  |
| test_state          | pass/fail                                                                                                  |
| first_fail_oracle   | the location of the assertion that fails first (if it is not nan, then the test fails due to an assertion) |


### Running the experiment
The Docker image is configured to run the analysis on a limited subset of mutants for commons-cli, providing a demonstration of how the analysis works.
For larger sets of mutants, the analysis can be extended by configuring PITest (https://pitest.org/), allowing users to customize the scope of the experiment as needed.


#### ARM-based machines
pull the Docker image: 
```
docker pull qinfendeheichi/icse2025_cli-arm:latest
```
run the Docker container (around 1 min):
```
docker run --name exp1_arm qinfendeheichi/icse2025_cli-arm
```
Acquiring the csv file to the current path:
```
docker cp exp1_arm:/commons-cli/project/target/csvData.csv .
```
The csvData.csv file contains the raw data of the analysis.


#### AMD-based machines
pull the Docker image:
```
docker pull qinfendeheichi/icse2025_cli-amd:latest
```
run the Docker container (around 1 min):
```
docker run --name exp1_amd qinfendeheichi/icse2025_cli-amd
```
Acquiring the csv file to the current path:
```
docker cp exp1_amd:/commons-cli/project/target/csvData.csv .
```
The csvData.csv file contains the raw data of the analysis rendering figures presented in RQ1.

## fine-grained memory state analysis
We configured the Docker image to run the analysis on a limited subset of mutants that showcase the assertion candidates' characteristics. 
Additionally, the Docker image includes the full dataset used to present results addressing RQ2–4 in the paper.

Please note that the complete analysis requires approximately 1 TB of disk space and may take several weeks to fully execute.
### ARM-based machines
Pull the Docker image:
```
docker pull qinfendeheichi/icse2025_text-arm:latest
```
Run the Docker container (around 7 min): 
```
docker run --name exp2_arm qinfendeheichi/icse2025_text-arm
```
Fetch the produced intermediate data to host machine (30s):
```
docker cp exp2_arm:/commons-text/project/target .
docker cp exp2_arm:/commons-text/project/getData.py 
```
Inspect Mutant Killing Status and Characteristics of Mutant Killing Assertion Candidates:
```
python3 getData.py
```
Acquire the full data accessible through the container to the host machine  (~45s, 2.4GB):
```
docker cp exp2_arm:/data .
```
Change working directory to data:
```
cd data
```
Produce table 2 in the paper, answering RQ2 and RQ3 (3min):
```
python3 RQ23.py
```

Produce table 3 in the paper, answering RQ4 (4min):
```
python3 RQ4.py
```
To expedite the process, the scripts and data presented above reflect the average results of running the greedy strategy twice, rather than the 20 iterations reported in the paper. 
While the results may vary slightly, they are expected to closely align with those documented in the paper.
### AMD-based machines

Pull the Docker image:
```
docker pull qinfendeheichi/icse2025_text-amd:latest
```
Run the Docker container (around 7 min):
```
docker run --name exp2_amd qinfendeheichi/icse2025_text-amd
```
Fetch the produced intermediate data to host machine (30s):
```
docker cp exp2_amd:/commons-text/project/target .
docker cp exp2_amd:/commons-text/project/getData.py .
```
Inspect Mutant Killing Status and Characteristics of Mutant Killing Assertion Candidates:
```
python3 getData.py
```
Acquire the full data accessible through the container to the host machine  (~45s, 2.4GB):
```
docker cp exp2_amd:/data .
```
Change working directory to data:
```
cd data
```
Produce table 2 in the paper, answering RQ2 and RQ3 (3min):
```
python3 RQ23.py
```

Produce table 3 in the paper, answering RQ4 (4min):
```
python3 RQ4.py
```
To expedite the process, the scripts and data presented above reflect the average results of running the greedy strategy twice, rather than the 20 iterations reported in the paper.
While the results may vary slightly, they are expected to closely align with those documented in the paper.


# Supplementary Pictures
In answering RQ1, we show the heatmaps for 6 subject projects, each showing the mutant killing capability of individual tests and assertions; and as promised in the paper, here we provide the supplementary high-quality figures for all subject projects.
The figures are stored in the `supplementary_pics` directory.
Specifically, individual figures are stored under `supplementary_pics/individual_pics` directory, and the combined figure is stored under `supplementary_pics/crossfire.pdf`.
For individual figures, the figures demonstrating each test's capability have names ending with "_all", while the figures demonstrating each assertion's capability have names ending with "_assertion".
For lossless displays of these figures, please use pdf reading software to open those figures.

# Motivating Examples

## A simple real life example in Commons-text
We provide one simple motivating example in this section to demonstrate the interesting crossfiring attribute of our approach.
The test case is in `/commons-text/src/test/java/org/apache/commons/text/ExtendedMessageFormatTest.java` for commons-text 1.10.0, in .

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

We also include an additional interesting motivating example in the `spotify-web-api` project, where one assertion amplified test case kills **57** alive mutants all at once through our analysis.
The new test case is reconstructed through `shouldReturnDefault_sync()`.
We provide the information on each added assertion's mutant kills (mutant IDs) in the code.
Note that it does not necessarily mean all these assertions should be organized into one single augmented test case, however, assertions can be derived based on one existing test case and each of them may kill and crossfire mutants.
The test case is located at `memory-state_analysis/subjects/spotify-web-api/src/test/java/se/michaelthelin/spotify/KillMutants.java`.

### Additional Data Structure Description

For each of the projects, the propagation data answering RQ2-RQ4 are stored in serialized Python objects in memory-state_analysis/data (also in Docker images), which can be queried using Python.
Specifically, each subject project contains the following pickle file, including `MemoryData`, `AccessibilityData`, `NMR_full_blacklist`, and `NMR_itemized_blacklist`.
For storage reasons, data from Jfreechart and Jline-reader are compressed into separate files (ending with .gz).
We explain the data structure of all of the data below:

### MemoryData
`MemoryData` is a dictionary that captures the overall memory state differences between test runs on the mutated program and the original program.

Here, the term "overall memory difference" refers to a comparison of the program states for all relevant variables. 
These variables are referenced in a list/array, and their entire program states are serialized and stored using XStream in a single operation.
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
`NMR_full_blacklist` stores a set of node ids that are blacklisted (non-deterministic) from the memory analysis, corresponding to `MemoryData`.

```plaintext
dict
|
+-- test_id (String Key)
        |
        +-- {Set of blacklisted node ids}
```

### AccessibilityData
`AccessibilityData` is also a dictionary, but it provides per-variable state difference information.

In contrast to `MemoryData`, which offers an overall state comparison, AccessibilityData focuses on the state differences for each relevant variable individually. 
It includes details on how polluted states can be accessed during each test case execution, along with all possible variables that can access the infection. 
This involves serializing and storing the state of each variable separately.

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

## Run the experiment step by step
The Docker image demonstrates the essential pipelines required to run the experiments. 
Additionally, it includes the Dockerfile used to configure the image, outlining key steps such as installing PITest, running mutation analysis, and executing our scripts.

To specifically install the modified version of PITest, execute the following:
```
mvn clean install -Dmaven.test.skip
```

Acquire the jar file for instrumenting the test classes, run:
```
mvn clean compile assembly:single
```

Each subject project included in the artifact has been configured and prepared for the experiment, the general pipeline is "compile, instrument, and run mutation analysis":
```
mvn clean compile test-compile
java -jar "p.jar" target/test-classes
mvn "-Dmaven.main.skip" pitest:mutationCoverage >info.txt 2>result.txt
```

The raw data related to per-mutant final program states are stored under `target/everything` directory for further analysis.