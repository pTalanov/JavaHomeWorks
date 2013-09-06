package ru.sbpau.talanov.task4;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * Task4 entry point.
 */
public final class Main {

    private static final int LISTENERS_OF_A_KIND_COUNT = 5;

    /**
     * task4 entry point. Runs a loop that calls fireEvent of TimeEvent and RandomEvent
     * with a few action listeners subscribed to them.
     *
     * @param args command line arguments (ignored).
     */
    public static void main(String[] args) {
        Event e1 = new RandomEvent();
        Event e2 = new TimeEvent();

        setUpListeners(e1, e2);

        try {
            while (true) {
                System.out.println("Random event firing!");
                e1.fireEvent();
                System.out.println("Time event firing!");
                e2.fireEvent();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted exception caught: " + e.getMessage());
        }
    }

    private static void setUpListeners(@NotNull Event... events) {
        for (Event event : events) {
            for (int i = 0; i < LISTENERS_OF_A_KIND_COUNT; i++) {
                final int listenerId = i;
                event.addListener(new ActionListener() {
                    @Override
                    public void preformAction(@NotNull Event e) {
                        System.out.println("subscriber#" + listenerId);
                    }
                });
                final Date creationTime = new Date(System.currentTimeMillis());
                event.addListener(new ActionListener() {
                    @Override
                    public void preformAction(@NotNull Event e) {
                        System.out.println("Subscriber creation time: " + creationTime);
                    }
                });
            }

        }
    }

}
