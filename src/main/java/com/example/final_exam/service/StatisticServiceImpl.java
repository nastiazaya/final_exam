package com.example.final_exam.service;

import com.example.final_exam.model.Country;
import com.example.final_exam.model.Currencies;
import com.example.final_exam.model.User;
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
    public List<Row> statisticCalculate(Instant from, Instant to){
        Dataset<Row> inputDF = betSparkRepository.readBetEvent();
        List<User> users = userRepository.findAll();
        Dataset<Row> usersDF = sparkSession.createDataFrame(users,User.class);
        Dataset<Row> enrichedWithUserInfoDf = inputDF.join(usersDF,inputDF.col("userId").equalTo(usersDF.col("id")));
        Dataset<Row> result = enrichedWithUserInfoDf.withColumn("win", when(col("currencyCode").equalTo(Currencies.EUR.toString()), col("win").divide(1.1)))
                .withColumn("bet", when(col("currencyCode").equalTo(Currencies.EUR.toString()), col("bet").divide(1.1)))
                .filter(not(col("countryOfOrigin").equalTo(Country.USA.toString()).and(col("gameName").contains("-demo"))))
                .filter(col("eventTime").$greater(lit(from)))
                .filter(col("eventTime").$less(lit(to)))
                .withColumn("profit",col("win").minus(col("bet")))
                .groupBy("gameName")
                .agg(
                        avg(col("bet").as("average_bet")),
                        max(col("bet").as("max_bet")),
                        min(col("bet").as("min_bet")),
                        avg(col("win").as("average_win")),
                        max(col("win").as("max_win")),
                        min(col("win").as("min_win")),
                        avg(col("profit").as("average_profit")),
                        max(col("profit").as("max_profit")),
                        min(col("profit").as("min_profit"))
                );
        return result.collectAsList();

    }


}
