package com.gramazski.craps.exception;

public class ReportException extends Exception {
    public ReportException() {
    }

    /**
     * @param message
     * @param exception
     */
    public ReportException(String message, Throwable exception) {
        super(message, exception);
    }

    /**
     * @param message
     */
    public ReportException(String message) {
        super(message);
    }

    /**
     * @param exception
     */
    public ReportException(Throwable exception) {
        super(exception);
    }
}
