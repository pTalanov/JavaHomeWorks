package ru.spbau.talanov.task1;

import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.io.IOException;

/**
 * Interface for writing messages.
 */
public interface MessageWriter extends Closeable {

    /**
     * Writes passed message to an underlying stream.
     *
     * @param message message to write
     * @throws java.io.IOException
     */
    void writeMessage(@NotNull Message message) throws IOException;

}
