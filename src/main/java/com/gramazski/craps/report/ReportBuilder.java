package com.gramazski.craps.report;

import com.gramazski.craps.entity.Entity;

import java.util.List;

public interface ReportBuilder<T extends Entity, K extends AbstractReport> {
    K generateReport(List<T> entitiesList);
}
