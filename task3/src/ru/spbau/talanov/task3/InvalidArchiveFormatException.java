package ru.spbau.talanov.task3;

/**
 * An exception that is thrown in case of invalid archive format.
 */
public class InvalidArchiveFormatException extends Exception {

    /**
     * Constructs a new InvalidArchiveFormatException.
     */
    public InvalidArchiveFormatException() {
    }

    /**
     * Constructs a new InvalidArchiveFormatException.
     *
     * @param message the detail message.
     */
    public InvalidArchiveFormatException(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidArchiveFormatException.
     *
     * @param cause the cause.
     */
    public InvalidArchiveFormatException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new InvalidArchiveFormatException.
     *
     * @param message the detail message.
     * @param cause   the cause.
     */
    public InvalidArchiveFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
