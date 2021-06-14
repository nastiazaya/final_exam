package com.example.final_exam.service;


import com.example.final_exam.model.Currencies;
import com.example.final_exam.repository.BetSparkRepository;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import static org.apache.spark.sql.functions.*;

import java.time.Instant;

@Service
public class SuspiciousActivity {

    @Autowired
    private SparkSession sparkSession;

    /*@Autowired
    private UserRepository userRepository;*/

    @Autowired
    private BetSparkRepository betSparkRepository;


    public Dataset<Row> findSuspiciousActivitiesForATimePeriod(Instant from, Instant to){
        Dataset<Row> inputDF = betSparkRepository.readBetEvent();

        Dataset<Row> timePeriodDf = inputDF.withColumn("win", when(col("currencyCode").equalTo(Currencies.EUR.toString()), col("win").divide(1.1)))
                .withColumn("bet", when(col("currencyCode").equalTo(Currencies.EUR.toString()), col("bet").divide(1.1)))
                .filter(col("eventTime").$greater(lit(from)))
                .filter(col("eventTime").$less(lit(to)))
                .withColumn("suspiciousActivity", when(col("win").divide(col("bet")).$greater(lit(0.1)),"yes").otherwise("no"))
                .withColumn("suspiciousActivity", when(col("onlineTimeSecs").$greater(lit(18000)),"yes").otherwise("no"));

 
        return timePeriodDf;


    }





}
