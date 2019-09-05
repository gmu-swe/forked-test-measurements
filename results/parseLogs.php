<?php
$systems = ["ant", "gradle", "mvn"];
print "Build System,Time window,Duration\n";
foreach ($systems as $build) {
    $lastEvent = null;
    $lastTime = 0;
    foreach (file("log.$build.txt") as $line) {
        if (preg_match("/TESTPROFILER,([^,]+),([^,]+),([^,]+)/", $line, $m)) {
            $event = $m[2];
            if($event == "beforeClass" || $event == "afterClass")
                continue;
            $time = $m[3];
            if($lastEvent != null){
                print "$build,$lastEvent-$event,".($time - $lastTime)."\n";
            }
            $lastEvent = $event;
            $lastTime =$time;
            if($event == "exit")
            {
                $lastEvent = null;
            }
        }
    }
}
?>
