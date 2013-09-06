package ru.spbau.talanov.task1;

/**
 * This exception is thrown by Message readers if message format appeared to be invalid.
 */
public class IllegalMessageFormatException extends Exception {

    /**
     * @param message a message that contains information about this exception.
     */
    public IllegalMessageFormatException(String message) {
        super(message);
    }

}
