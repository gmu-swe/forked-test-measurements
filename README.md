# forked-test-measurements

To run:

1. Install instrumentation project

```sh
mvn clean install --file build-instrumenter/pom.xml
```

Look for `maven-install-plugin` command line output like

    Installing /home/jon/code/dhis2/forked-test-measurements/build-instrumenter/target/build-system-profiler-1.0.0-SNAPSHOT-jar-with-dependencies.jar to /home/jon/.m2/repository/edu/gmu/swe/junit/measurement/build-system-profiler/1.0.0-SNAPSHOT/build-system-profiler-1.0.0-SNAPSHOT-jar-with-dependencies.jar

which tells you where to the agent was installed

2. Make the jar location available in an environment variable

```sh
export INST_JAR_LOC=<jar install destination>
```

**Make sure to use the location to the jar with-dependencies!**

3. Set environmental variable

```sh
export JAVA_TOOL_OPTIONS="-javaagent:$INST_JAR_LOC -Xbootclasspath/a:$INST_JAR_LOC"
```

4. Run each build, collect output to file in `results/log.ant|mvn|gradle.txt`
5. In `results` run `php parseLogs.php`

Results:

|                      | Time in msec            |                  |                              |
|---------------------|--------------------------|------------------|------------------------------|
|           | Time to start forked JVM | Time to run test | Time to tear down forked JVM |
| ant                 | 250.42                   | 252.81           | 8.75                         |
| gradle              | 394.91                   | 253.12           | 16.9                         |
| mvn                 | 244.44                   | 253.02           | 351.61                       |
