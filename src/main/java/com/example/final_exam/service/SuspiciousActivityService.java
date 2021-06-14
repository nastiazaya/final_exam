package com.example.final_exam.service;

import com.example.final_exam.model.spark.SuspiciousActivityReport;
import org.apache.spark.sql.Row;

import java.time.Instant;
import java.util.List;

public interface SuspiciousActivityService {
    List<SuspiciousActivityReport> findSuspiciousActivitiesForATimePeriod(Instant from, Instant to);
}
