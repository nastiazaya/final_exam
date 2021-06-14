package com.example.final_exam.service;

import org.apache.spark.sql.DataFrame;

import java.time.Instant;

public interface StatisticService {
    DataFrame statisticCalculate(Instant from, Instant to);
}
