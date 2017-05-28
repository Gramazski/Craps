package com.gramazski.craps.service;

import com.gramazski.craps.entity.impl.PlayedBet;
import com.gramazski.craps.exception.HandlerException;
import com.gramazski.craps.exception.ReportException;
import com.gramazski.craps.report.AbstractReport;
import com.gramazski.craps.report.ReportBuilder;
import com.gramazski.craps.report.ReportBuilderManager;
import com.gramazski.craps.report.ReportType;
import com.gramazski.craps.util.CipherHandler;
import com.gramazski.craps.util.DateHandler;
import com.gramazski.craps.util.FileCreator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ReportingService {
    private final static Logger logger = LogManager.getLogger(ReportingService.class);
    private static final String RESOURCE_DIRECTORY = "assets/img/user/";

    public String buildReport(List<PlayedBet> playedBets, String userName, String rootPath){
        ReportBuilder reportBuilder = ReportBuilderManager.getInstance().getReportBuilder(ReportType.EXCEL);
        AbstractReport report = reportBuilder.generateReport(playedBets);
        String path = null;

        try {
            path = FileCreator.saveToFile(report.getBytePresentation(),
                    RESOURCE_DIRECTORY + userName + CipherHandler.encryptString(DateHandler.getCurrentDateTime()) + ".xls",
                    rootPath);
        } catch (HandlerException | ReportException e) {
            logger.log(Level.ERROR, e.getMessage());
        }

        return path;
    }
}
