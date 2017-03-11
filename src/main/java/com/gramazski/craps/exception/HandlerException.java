package com.gramazski.craps.exception;

/**
 * Created by gs on 21.02.2017.
 */
public class HandlerException extends Exception {
    public HandlerException() {
    }

    public HandlerException(String message, Throwable exception) {
        super(message, exception);
    }

    public HandlerException(String message) {
        super(message);
    }

    public HandlerException(Throwable exception) {
        super(exception);
    }
}
