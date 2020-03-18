package pacman_infd;

import org.junit.jupiter.api.Test;
import pacman_infd.Games.StopWatch;

import java.util.concurrent.TimeUnit;

public class StopWatchTest {
    @Test
    public void testStopWatch() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            TimeUnit.SECONDS.sleep(1);
            assert stopWatch.getElapsedTimeMinutesSeconds().equals("00:01");
        } catch (InterruptedException e) {
            stopWatch.stop();
        }

        if (stopWatch.isRunning()) {
            stopWatch.stop();
        }

        assert(stopWatch.getElapsedTime() > 0);
    }

    @Test
    public void testFormatElapsed() {
        StopWatch stopWatch = new StopWatch(60000L);

        assert stopWatch.getElapsedTimeMinutesSeconds().equals("01:00");
    }
}
