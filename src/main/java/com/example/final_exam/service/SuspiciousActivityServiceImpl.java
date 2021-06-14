package com.example.final_exam.service;


import com.example.final_exam.model.Country;
import com.example.final_exam.model.Currencies;
import com.example.final_exam.model.jpa.User;
import com.example.final_exam.model.spark.SuspiciousActivityReport;
import com.example.final_exam.repository.BetSparkRepository;
import com.example.final_exam.repository.UserRepository;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.apache.spark.sql.functions.*;

import java.time.Instant;
import java.util.List;

@Service
public class SuspiciousActivityServiceImpl implements SuspiciousActivityService {

    @Autowired
    private SparkSession sparkSession;


    @Autowired
    private BetSparkRepository betSparkRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<SuspiciousActivityReport> findSuspiciousActivitiesForATimePeriod(Instant from, Instant to){
        Dataset<Row> inputDF = betSparkRepository.readBetEvent();
        List<User> users = userRepository.findAll();
        Dataset<Row> usersDF = sparkSession.createDataFrame(users,User.class);
        Dataset<Row> enrichedWithUserInfoDf = inputDF
                .join(usersDF,inputDF.col("userId").equalTo(usersDF.col("id")))
                .drop("id");
        System.out.println("enrichedWithUserInfoDf");
        enrichedWithUserInfoDf.show();

        enrichedWithUserInfoDf.printSchema();
        Dataset<Row> suspiciousActivities = enrichedWithUserInfoDf
                .withColumn("win", when(col("eventCurrencyCode").equalTo(Currencies.EUR.toString()), col("win").divide(1.1)).otherwise(col("win")))
                .withColumn("bet", when(col("eventCurrencyCode").equalTo(Currencies.EUR.toString()), col("bet").divide(1.1)).otherwise(col("bet")))
                .filter(not(col("countryOfOrigin").equalTo(Country.USA.toString()).and(col("gameName").contains("-demo"))))
                .filter(col("eventTime").$greater(lit(from)))
                .filter(col("eventTime").$less(lit(to)))
                .groupBy("userId","email","name","lastName","countryOfOrigin")
                .agg(
                        approx_count_distinct(col("eventCountry")).as("betsMadeFromCountriesCount"),
                        sum(col("win")).as("win"),
                        sum(col("bet")).as("bet"),
                        sum(col("onlineTimeSecs")).as("onlineTimeSecs")
                        )
                .withColumn("suspiciousActivity",when(col("betsMadeFromCountriesCount").$greater(lit(1)),"yes").otherwise("no"))
                .withColumn("suspiciousActivity", when(col("win").divide(col("bet")).$greater(lit(0.1)),"yes").otherwise("no"))
                .withColumn("suspiciousActivity", when(col("onlineTimeSecs").$greater(lit(18000)),"yes").otherwise("no"))
                .filter(col("suspiciousActivity").equalTo("yes"))
                .drop("suspiciousActivity");
        System.out.println("suspiciousActivities");
        suspiciousActivities.show();
        suspiciousActivities.printSchema();
        return suspiciousActivities.as(Encoders.bean(SuspiciousActivityReport.class)).collectAsList();
    }





}
