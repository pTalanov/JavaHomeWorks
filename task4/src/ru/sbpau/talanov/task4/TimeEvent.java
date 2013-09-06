package ru.sbpau.talanov.task4;

/**
 * An event that is ready to be fired each 10 seconds.
 */
public final class TimeEvent extends Event {

    private static final long PERIOD_MILLISECONDS = 10000;

    private long lastTime = 0;

    /**
     * Constructs a new TimeEvent object.
     */
    public TimeEvent() {
    }

    /**
     * Returns true if this TimeEvent is ready to be fired.
     * TimeEvent is ready to be fired if at least 10 seconds passed since last event was fired.
     *
     * @return true if this TimeEvent is ready to be fired, else false.
     */
    @Override
    public boolean ready() {
        long currentTime = System.currentTimeMillis();
        boolean isReady = PERIOD_MILLISECONDS <= currentTime - lastTime;
        lastTime = currentTime;
        return isReady;
    }
}
