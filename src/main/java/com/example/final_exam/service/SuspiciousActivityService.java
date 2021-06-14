package com.example.final_exam.service;

import org.apache.spark.sql.Row;

import java.time.Instant;
import java.util.List;

public interface SuspiciousActivityService {
    List<Row> findSuspiciousActivitiesForATimePeriod(Instant from, Instant to);
}
