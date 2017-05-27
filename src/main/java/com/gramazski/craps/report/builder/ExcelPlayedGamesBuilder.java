package com.gramazski.craps.report.builder;

import com.gramazski.craps.entity.impl.PlayedBet;
import com.gramazski.craps.report.ReportBuilder;
import com.gramazski.craps.report.entity.ExcelReport;

import java.util.List;

public class ExcelPlayedGamesBuilder implements ReportBuilder<PlayedBet, ExcelReport> {
    @Override
    public ExcelReport generateReport(List<PlayedBet> entitiesList) {
        String[] values = new String[]{
                "Lobby", "Time", "Bet", "Amount", "BetType", "Type"
        };
        ExcelReport excelReport = new ExcelReport();
        excelReport.addRow(values, 0);

        for (int i = 0; i < entitiesList.size(); i++){
            excelReport.addRow(createStringFieldsArray(entitiesList.get(i)), i + 1);
        }

        return excelReport;
    }

    private String[] createStringFieldsArray(PlayedBet playedBet){
        return new String[]{
                playedBet.getLobby(), playedBet.getTime(), String.valueOf(playedBet.getBet()),
                String.valueOf(playedBet.getAmount()), playedBet.getBetType(), playedBet.getType()
        };
    }
}
