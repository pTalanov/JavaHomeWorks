package ru.spbau.talanov.task1;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;

/**
 * Reads Message objects from file.
 */
public final class FileMessageReader implements Closeable {

    @NotNull
    private final BufferedReader fileReader;

    /**
     * Constructs a FileMessageReader. Uses a file identified by passed file path.
     *
     * @param filePath path to the file that reader should use.
     * @throws java.io.FileNotFoundException if file was not found.
     */
    public FileMessageReader(@NotNull String filePath) throws FileNotFoundException {
        this(new File(filePath));
    }

    /**
     * Constructs a FileMessageReader. Uses a file identified by passed File object.
     *
     * @param file file to be read
     * @throws java.io.FileNotFoundException if file was not found.
     */
    public FileMessageReader(@NotNull File file) throws FileNotFoundException {
        this.fileReader = new BufferedReader(new FileReader(file));
    }

    /**
     * Reads next message from underlying stream.
     *
     * @return a message read or null if eof was reached.
     * @throws java.io.IOException           if an I/O error occurs.
     * @throws IllegalMessageFormatException if a message has invalid format.
     */
    @Nullable
    public Message readMessage() throws IOException, IllegalMessageFormatException {
        Integer linesNumber = readLinesNumber();
        if (linesNumber == null) {
            return null;
        }
        if (linesNumber < 0) {
            throw new IllegalMessageFormatException("Message lines number parse error occurred."
                    + "Lines number has to be positive.");
        }
        return readNextMessage(linesNumber);
    }

    /**
     * Closes the underlying file stream.
     *
     * @throws java.io.IOException in case of stream close failure.
     */
    @Override
    public void close() throws IOException {
        fileReader.close();
    }

    @Nullable
    private Integer readLinesNumber() throws IOException, IllegalMessageFormatException {
        String line = fileReader.readLine();
        if (line == null) {
            return null;
        }
        try {
            return Integer.parseInt(line.trim());
        } catch (NumberFormatException e) {
            System.err.println("Message lines number parse failed: " + e.getMessage());
            throw new IllegalMessageFormatException("Message lines number parse error occurred. Unexpected tokens: "
                    + line);
        }
    }

    @NotNull
    private Message readNextMessage(int linesExpected) throws IOException, IllegalMessageFormatException {
        int linesRead = 0;
        Message message = new Message();

        while (linesRead < linesExpected) {
            String line = fileReader.readLine();
            if (line == null) {
                throw new IllegalMessageFormatException("Message parse error occurred. Unexpected end of file.");
            }
            message.append(line);
            linesRead++;
        }
        return message;
    }

}
