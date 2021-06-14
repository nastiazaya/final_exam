package com.example.final_exam.service;

import com.example.final_exam.model.Country;
import com.example.final_exam.model.Currencies;
import com.example.final_exam.model.jpa.User;
import com.example.final_exam.model.spark.StatisticReport;
import com.example.final_exam.repository.BetSparkRepository;

import com.example.final_exam.repository.UserRepository;
import org.apache.spark.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static org.apache.spark.sql.functions.*;

@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private BetSparkRepository betSparkRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SparkSession sparkSession;

    @Override
    public List<StatisticReport> statisticCalculate(Instant from, Instant to){
        Dataset<Row> inputDF = betSparkRepository.readBetEvent();
        List<User> users = userRepository.findAll();
        Dataset<Row> usersDF = sparkSession.createDataFrame(users,User.class);
        Dataset<Row> enrichedWithUserInfoDf = inputDF
                .join(usersDF,inputDF.col("userId").equalTo(usersDF.col("id")))
                .drop("id");
        System.out.println("enrichedWithUserInfoDf");
        Dataset<Row> result = enrichedWithUserInfoDf.withColumn("win", when(col("eventCurrencyCode").equalTo(Currencies.EUR.toString()), col("win").divide(1.1)).otherwise(col("win")))
                .withColumn("bet", when(col("eventCurrencyCode").equalTo(Currencies.EUR.toString()), col("bet").divide(1.1)).otherwise(col("bet")))
                .filter(not(col("countryOfOrigin").equalTo(Country.USA.toString()).and(col("gameName").contains("-demo"))))
                .filter(col("eventTime").$greater(lit(from)))
                .filter(col("eventTime").$less(lit(to)))
                .withColumn("profit",col("win").minus(col("bet")))
                .groupBy("gameName")
                .agg(
                        avg(col("bet")).as("averageBet"),
                        max(col("bet")).as("maxBet"),
                        min(col("bet")).as("minBet"),
                        avg(col("win")).as("averageWin"),
                        max(col("win")).as("maxWin"),
                        min(col("win")).as("minWin"),
                        avg(col("profit")).as("averageProfit"),
                        max(col("profit")).as("maxProfit"),
                        min(col("profit")).as("minProfit")
                );
        return result.as(Encoders.bean(StatisticReport.class)).collectAsList();
    }


}
