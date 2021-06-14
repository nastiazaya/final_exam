package com.example.final_exam.service;


import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.time.Instant;
import java.util.List;

public interface StatisticService {
    List<Row> statisticCalculate(Instant from, Instant to);
}
