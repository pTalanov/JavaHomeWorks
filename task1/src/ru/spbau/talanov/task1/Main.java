package ru.spbau.talanov.task1;

import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Main class for task1.
 */
public final class Main {

    /**
     * Task1 entry point.
     * Argument are interpreted as follows:
     * first argument - input file name,
     * second argument (optional) - output file name.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: Main <input_file_path> [output_file_path]");
            return;
        }
        String inputFilePath = args[0];
        try (FileMessageReader messageReader = new FileMessageReader(inputFilePath)) {
            try (MessageWriter writer = getWriter(args)) {
                compress(messageReader, writer);
            } catch (FileNotFoundException e) {
                System.err.println("Output file not found");
            } catch (IOException e) {
                System.out.println("IOException while writing. Message: " + e.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found.");
        } catch (IOException e) {
            System.out.println("IOException while reading. Message: " + e.getMessage());
        }
    }

    @NotNull
    private static MessageWriter getWriter(@NotNull String[] args) throws FileNotFoundException {
        if (args.length > 1) {
            String outputFilePath = args[1];
            return new CompressingMessageWriter(new FileMessageWriter(outputFilePath));
        } else {
            return new CompressingMessageWriter(new ConsoleMessageWriter());
        }
    }

    private static void compress(@NotNull FileMessageReader reader, @NotNull MessageWriter writer) {
        try {
            Message message = reader.readMessage();
            while (message != null) {
                writer.writeMessage(message);
                message = reader.readMessage();
            }
        } catch (IllegalMessageFormatException e) {
            System.err.println("Exception caught: illegal message format. Message: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Exception caught: io exception. Message: " + e.getMessage());
        }
    }
}
