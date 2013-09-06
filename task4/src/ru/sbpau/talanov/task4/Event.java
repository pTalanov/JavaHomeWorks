package ru.sbpau.talanov.task4;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Abstract event that can be ready to be fired, can be fired and can accept more event listeners.
 */
public abstract class Event {
    @NotNull
    private final Set<ActionListener> listeners = new HashSet<>();

    /**
     * Abstract event constructor.
     */
    protected Event() {
    }

    /**
     * Returns true if this event is ready to be fired.
     *
     * @return true if and only if this event is ready to be fired.
     */
    public abstract boolean ready();

    /**
     * Subscribes passed action listener to this object's events.
     *
     * @param actionListener a listener to subscribe.
     */
    public final void addListener(@NotNull ActionListener actionListener) {
        listeners.add(actionListener);
    }

    /**
     * Fires this event notifying all subscribed listeners if this event object's ready method returns true.
     */
    public final void fireEvent() {
        if (ready()) {
            for (ActionListener al : listeners) {
                al.preformAction(this);
            }
        }
    }
}
