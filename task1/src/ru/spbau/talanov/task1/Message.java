package ru.spbau.talanov.task1;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A message which consists of multiple lines.
 */
public final class Message {

    @NotNull
    private List<String> lines = new ArrayList<>();

    /**
     * Appends specified line to the end of this message.
     *
     * @param line line to append
     */
    public void append(@NotNull String line) {
        lines.add(line);
    }

    /**
     * Appends all lines of passed Message to this message.
     *
     * @param message message to append
     */
    public void append(@NotNull Message message) {
        lines.addAll(message.lines);
    }

    /**
     * Returns lines of this message.
     *
     * @return list of lines of this message
     */
    @NotNull
    public List<String> getLines() {
        return Collections.unmodifiableList(lines);
    }

}
