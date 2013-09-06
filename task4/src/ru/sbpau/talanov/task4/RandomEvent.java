package ru.sbpau.talanov.task4;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * An event that is ready to be fired at random moments in time.
 */
public class RandomEvent extends Event {

    @NotNull
    private final static Random RANDOM = new Random();

    /**
     * Constructs a new RandomEvent object.
     */
    public RandomEvent() {
    }

    /**
     * Returns true if this event is ready to be fired.
     * The event is ready if it's internal random generated number
     *
     * @return true if and only if this event is ready to be fired.
     */
    @Override
    public boolean ready() {
        return 0 == RANDOM.nextLong() % 2;
    }

}
