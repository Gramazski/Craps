package com.gramazski.craps.report;

import com.gramazski.craps.exception.ReportException;

public abstract class AbstractReport {
    public abstract void addRow(String[] values, int rowNumber);
    public abstract byte[] getBytePresentation() throws ReportException;
}
