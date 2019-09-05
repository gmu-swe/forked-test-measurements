<?php
$count = 100;
$dir = "../gradle-benchmark";

$times = [250];
$dir .= "/src/test/java/edu/gmu/swe/junit/measurement/";
$template = file_get_contents("SlowTest.template.java");
for ($i = 0; $i < $count; $i++) {
    foreach ($times as $time) {
        file_put_contents($dir . "/SlowTest" . $i . "_" . $time . "msecTest.java", str_replace('$TIME', $time, str_replace('$IDX', $i, $template)));
    }
}
?>