package ru.spbau.talanov.task1;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

/**
 * Writes formatted messages to a file.
 */
public final class FileMessageWriter implements MessageWriter {

    @NotNull
    private final PrintStream out;

    /**
     * Constructs a FileMessageWriter. Uses a file identified by passed file path.
     *
     * @param filePath path to the file that writer should use.
     * @throws java.io.FileNotFoundException
     */
    public FileMessageWriter(@NotNull String filePath) throws FileNotFoundException {
        this(new File(filePath));
    }

    /**
     * Constructs a FileMessageWriter. Uses a file identified by passed File object.
     *
     * @param file a file to write messages to.
     * @throws java.io.FileNotFoundException if a file was not found.
     */
    public FileMessageWriter(@NotNull File file) throws FileNotFoundException {
        this.out = new PrintStream(file);
    }

    @NotNull
    private String messageToString(@NotNull Message m) {
        StringBuilder sb = new StringBuilder();
        List<String> lines = m.getLines();

        sb.append(lines.size());
        sb.append(System.lineSeparator());

        for (String line : lines) {
            sb.append(line);
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * Closes underlying file stream.
     *
     * @throws IOException if an exception is thrown while closing the stream
     */
    @Override
    public void close() throws IOException {
        out.close();
    }

    /**
     * Writes a message to underlying print stream.
     *
     * @param message a message to write
     * @throws java.io.IOException if an I/O error occurs.
     */
    @Override
    public void writeMessage(@NotNull Message message) throws IOException {
        out.print(messageToString(message));
    }
}
