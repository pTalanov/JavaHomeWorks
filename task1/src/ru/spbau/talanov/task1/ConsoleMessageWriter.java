package ru.spbau.talanov.task1;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Writes formatted messages to the standard output.
 */
public final class ConsoleMessageWriter implements MessageWriter {

    private int messageCount;

    @NotNull
    private String messageToString(@NotNull Message m) {
        StringBuilder sb = new StringBuilder();
        int linesPrinted = 0;

        sb.append("\"Message ");
        sb.append(++messageCount);
        sb.append('"');
        sb.append(System.getProperty("line.separator"));

        for (String line : m.getLines()) {
            sb.append('"');
            sb.append(messageCount);
            sb.append('.');
            sb.append(++linesPrinted);
            sb.append(". ");
            sb.append(line);
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }

    /**
     * Writes a message to underlying print stream.
     *
     * @param message a message
     * @throws java.io.IOException if an I/O error occurs.
     */
    @Override
    public void writeMessage(@NotNull Message message) throws IOException {
        System.out.print(messageToString(message));
    }

    /**
     * Does nothing as console stream should not be closed.
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        //do nothing
    }
}
