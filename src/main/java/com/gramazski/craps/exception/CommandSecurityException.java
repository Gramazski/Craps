package com.gramazski.craps.exception;

public class CommandSecurityException extends Exception {
    public CommandSecurityException() {
    }

    /**
     * @param message
     * @param exception
     */
    public CommandSecurityException(String message, Throwable exception) {
        super(message, exception);
    }

    /**
     * @param message
     */
    public CommandSecurityException(String message) {
        super(message);
    }

    /**
     * @param exception
     */
    public CommandSecurityException(Throwable exception) {
        super(exception);
    }
}
