package edu.gmu.swe.junit.measurement;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SlowTest$IDX_$TIMEmsecTest {
    @BeforeClass
    public static void logStartTime() {
        System.out.println("TESTPROFILER,SlowTest$TIME,beforeClass," + System.currentTimeMillis());
    }

    @Test
    public void testTakes$TIME() throws Exception {
        System.out.println("TESTPROFILER,SlowTest$TIME,testStart," + System.currentTimeMillis());
        Thread.sleep($TIME);
        System.out.println("TESTPROFILER,SlowTest$TIME,testEnd," + System.currentTimeMillis());
    }

    @AfterClass
    public static void logEndTime() {
        System.out.println("TESTPROFILER,SlowTest$TIME,afterClass," + System.currentTimeMillis());
    }
}
