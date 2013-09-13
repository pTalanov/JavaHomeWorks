package ru.spbau.talanov.task7;

/**
 * Exception occuring during serialization or deserialization.
 */
public class SerializerException extends Exception {

    /**
     * Constructs a new SerializationException object.
     *
     * @param message details message.
     * @param cause   exception which caused serialization to fail.
     */
    public SerializerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerializerException(String message) {
        super(message);
    }
}
