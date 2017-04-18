package com.gramazski.craps.exception;

/**
 * Created by gs on 21.02.2017.
 */
public class HandlerException extends Exception {
    public HandlerException() {
    }

    /**
     * @param message
     * @param exception
     */
    public HandlerException(String message, Throwable exception) {
        super(message, exception);
    }

    /**
     * @param message
     */
    public HandlerException(String message) {
        super(message);
    }

    /**
     * @param exception
     */
    public HandlerException(Throwable exception) {
        super(exception);
    }
}
