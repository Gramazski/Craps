package com.gramazski.craps.report;

import com.gramazski.craps.report.builder.ExcelPlayedGamesBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ReportBuilderManager {
    private Map<ReportType, ReportBuilder> builderMap;
    private static ReportBuilderManager instance;
    private static AtomicBoolean instanceFlag = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();

    private ReportBuilderManager(){
        builderMap = new HashMap<>();
        builderMap.put(ReportType.EXCEL, new ExcelPlayedGamesBuilder());
    }

    public static ReportBuilderManager getInstance(){
        if (!instanceFlag.get()){
            lock.lock();
            try {
                if (!instanceFlag.get()){
                    instance = new ReportBuilderManager();
                    instanceFlag.getAndSet(true);
                }
            }
            finally {
                lock.unlock();
            }

        }

        return instance;
    }

    public ReportBuilder getReportBuilder(ReportType reportType){
        if (builderMap.containsKey(reportType)){
            return builderMap.get(reportType);
        }

        return null;
    }
}
