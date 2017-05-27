package com.gramazski.craps.report.entity;

import com.gramazski.craps.exception.ReportException;
import com.gramazski.craps.report.AbstractReport;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;

public class ExcelReport extends AbstractReport {
    private Workbook workbook;
    private Sheet sheet;

    public ExcelReport(){
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet();
    }

    public void addRow(String[] values, int rowNumber){
        Row row = sheet.createRow(rowNumber);
        for (int i = 0; i < values.length; i++){
            Cell valueCell = row.createCell(i);
            valueCell.setCellValue(values[i]);
        }
    }

    public byte[] getBytePresentation() throws ReportException {
        ByteOutputStream stream = new ByteOutputStream();
        try {
            workbook.write(stream);
        } catch (IOException e) {
            throw new ReportException(e.getMessage(), e);
        }

        return stream.getBytes();
    }
}
