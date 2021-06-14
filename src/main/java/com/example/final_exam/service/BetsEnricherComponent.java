package com.example.final_exam.service;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface BetsEnricherComponent {

    Dataset<Row> enrichBetsDataWithUserInfo(Dataset<Row> betsDf);
}
