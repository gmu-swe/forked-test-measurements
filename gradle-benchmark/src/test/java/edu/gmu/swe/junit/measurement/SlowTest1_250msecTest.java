package edu.gmu.swe.junit.measurement;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SlowTest1_250msecTest {
    @BeforeClass
    public static void logStartTime() {
        System.out.println("TESTPROFILER,SlowTest250,beforeClass," + System.currentTimeMillis());
    }

    @Test
    public void testTakes250() throws Exception {
        System.out.println("TESTPROFILER,SlowTest250,testStart," + System.currentTimeMillis());
        Thread.sleep(250);
        System.out.println("TESTPROFILER,SlowTest250,testEnd," + System.currentTimeMillis());
    }

    @AfterClass
    public static void logEndTime() {
        System.out.println("TESTPROFILER,SlowTest250,afterClass," + System.currentTimeMillis());
    }
}
