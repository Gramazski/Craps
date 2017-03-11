package com.gramazski.craps.exception;

/**
 * Created by gs on 21.02.2017.
 */
public class DAOException extends Exception {
    public DAOException() {
    }

    public DAOException(String message, Throwable exception) {
        super(message, exception);
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(Throwable exception) {
        super(exception);
    }
}
