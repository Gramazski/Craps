package com.gramazski.craps.exception;

/**
 * Created by gs on 21.02.2017.
 */
public class ResourceManagerException extends Exception {
    public ResourceManagerException() {
    }

    /**
     * @param message
     * @param exception
     */
    public ResourceManagerException(String message, Throwable exception) {
        super(message, exception);
    }

    /**
     * @param message
     */
    public ResourceManagerException(String message) {
        super(message);
    }

    /**
     * @param exception
     */
    public ResourceManagerException(Throwable exception) {
        super(exception);
    }
}
