package com.example.final_exam.service;


import com.example.final_exam.model.spark.StatisticReport;

import java.time.Instant;
import java.util.List;

public interface StatisticService {
    List<StatisticReport> statisticCalculate(Instant from, Instant to);
    List<StatisticReport> statisticCalculate(Instant from, Instant to, String gameName);
}
