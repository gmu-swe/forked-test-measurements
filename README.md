# forked-test-measurements

To run:
1. Install instrumentation project, resulting jar goes to `$INST_JAR_LOC` (e.g. /Users/jon/.m2/repository/edu/gmu/swe/junit/measurement/BuildSystemProfiler/0.0.1-SNAPSHOT/BuildSystemProfiler-0.0.1-SNAPSHOT.jar)
2. Set environmental variable `JAVA_TOOL_OPTIONS="-javaagent:$INST_JAR_LOC -Xbootclasspath/p:$INST_JAR_LOC`
3. Run each build, collect output to file in `results/log.ant|mvn|gradle.txt`
4. In `results` run `php parseLogs.php`

