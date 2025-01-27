The code is modified based on PIT 1.10.3

# This project should work with these conditions:
1) single module 
2) maven project
3) PIT should be run with single thread
4) Experimented projects should have limited access to files
5) InstrumentationUtils.java should be included under `test/java` directory so that no mutations are generated on generated code.
6) All test classes' name should start with `Test` or end with `Test`

# Some situations might be handled when running the experiments:
1) test cases fail when asserting "time”

# Static Field Analyzer:
1) analysis is based on 1st Party Project Bytecode with "GETSTATIC" or "PUTSTATIC" instructions.


# Files generated during the exeperiment:
1```target/mutatedDesc.txt```
    to record the mutated method's name on the fly for state instrumentation

2```target/staticFields.txt```
    to record static fields accessed.
3```target/failingInOriginal.txt```
    to record test runs that fail in 10 separate original test runs without mutations. They are recorded here.
    the mutation's hashcode is used to specify a unique mutation.
4```target/NMR.txt```
probe info in no mutation run.
5```target/MR.txt```
probe info in mutation run.
6```target/MRs.txt```
temporary accumulated MR probe info for one mutation
7```target/NMRs.txt```
temporary accumulated NMR probe info for one mutation
8```target/GlobalStates.txt```
if it's doing original test run or mutation test run


# Exceptions:
1) when mutations are executed, the probes might not be executed even the test case pass. It is because an expected exception could be thrown and caught. They might be handled properly.
2) There shouldn't be any test dependency, or the static fields would change


Extra Dependencies
```
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.12.0</version>
        </dependency>
```

```
        <dependency>
            <groupId>com.thoughtworks.xstream</groupId>
            <artifactId>xstream</artifactId>
            <version>1.4.19</version>
        </dependency>
```

if rat-check plugin is present,

```
        <plugin>
          <groupId>org.apache.rat</groupId>
          <artifactId>apache-rat-plugin</artifactId>
          <configuration>
            <excludes>
              <exclude>src/test/java/InstrumentationUtils.java</exclude>
            </excludes>
          </configuration>
        </plugin>

```
Move instrumentationUtils.java to src/test/java directory






