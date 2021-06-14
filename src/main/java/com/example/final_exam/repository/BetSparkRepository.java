package com.example.final_exam.repository;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface BetSparkRepository {
    Dataset<Row> readBetEvent();
}
