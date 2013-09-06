package ru.sbpau.talanov.task4;

import org.jetbrains.annotations.NotNull;

/**
 * An interface for objects which intend to subscribe to Event's events.
 */
public interface ActionListener {

    /**
     * Called by Event descendants if an event occurs.
     *
     * @param e an object that issued the event.
     */
    void preformAction(@NotNull Event e);

}
