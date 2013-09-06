package ru.spbau.talanov.task1;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

/**
 * A decorator that can be used with other MessageWriters.
 * Concatenates subsequent message pairs.
 */
public final class CompressingMessageWriter implements MessageWriter {

    @NotNull
    private MessageWriter messageWriter;
    @Nullable
    private Message lastMessage;

    /**
     * @param messageWriter a message writer to use.
     */
    public CompressingMessageWriter(@NotNull MessageWriter messageWriter) {
        this.messageWriter = messageWriter;
    }

    /**
     * Writes messages in pairs
     *
     * @param message message to write
     * @throws java.io.IOException if an I/O error occurs.
     */
    @Override
    public void writeMessage(@NotNull Message message) throws IOException {
        if (lastMessage == null) {
            lastMessage = message;
        } else {
            lastMessage.append(message);
            messageWriter.writeMessage(lastMessage);
            lastMessage = null;
        }
    }

    /**
     * Attempts to write an unpaired message if there is one and closes the underlying MessageWriter.
     *
     * @throws java.io.IOException if an I/O error occurs on close() method call of underlying MessageWriter.
     */
    @Override
    public void close() throws IOException {
        try {
            if (lastMessage != null) {
                messageWriter.writeMessage(lastMessage);
                lastMessage = null;
            }
        } catch (IOException e) {
            System.err.println("Exception caught on attempt to write unpaired message.");
        } finally {
            messageWriter.close();
        }
    }
}
