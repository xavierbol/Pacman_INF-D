

package pacman_infd.games;

/**
 *
 * @author remcoruijsenaars
 */
public class StopWatch {
    private long startTime;
    private long elapsedTime;
    
    private boolean isRunning;

    /**
     * Default constructor of this class.
     */
    public StopWatch() {
        reset();
    }

    /**
     * Constructor of this class.
     *
     * @param elapsedTime the elapsed time.
     */
    public StopWatch(long elapsedTime) {
        isRunning = false;
        this.elapsedTime = elapsedTime;
    }

    /**
     * Check if the stopwatch is running.
     *
     * @return True if the stopwatch is running, otherwise return false.
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Reset the stopwatch.
     */
    public void reset() {
        elapsedTime = 0;
        isRunning = false;
    }

    /**
     * Start the stopwatch.
     */
    public void start() {
        if (!isRunning) {
            isRunning = true;
            startTime = System.currentTimeMillis();
        }
    }

    /**
     * Stop the stopwatch
     */
    public void stop() {
        if (isRunning) {
           isRunning = false;
           long stopTime = System.currentTimeMillis();
           elapsedTime = elapsedTime + stopTime - startTime;
        }
    }

    /**
     * Restart the stopwatch.
     */
    public void restart() {
        if (!isRunning) {
            isRunning = true;
            startTime = System.currentTimeMillis() - elapsedTime;
            elapsedTime = 0;
        }
    }

    /**
     * Get the elapsed time.
     *
     * @return The elapsed time.
     */
    public long getElapsedTime() {
        if (isRunning) {
            long endTime = System.currentTimeMillis();
            return elapsedTime + endTime - startTime;
        } else {
            return elapsedTime;
        }
    }

    /**
     * Get the elapsed time in minutes and seconds.
     *
     * @return the formatted elapsed time.
     */
    public String getElapsedTimeMinutesSeconds(){
        long time = getElapsedTime();
        long seconds = (time / 1000) % 60;
        long minutes = (time / (1000 * 60)) % 60;
        
        return String.format("%02d:%02d", minutes, seconds);
    }
}
