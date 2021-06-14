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
        Dataset<Row> betsDataDf = getBetsData(from, to);
        Dataset<Row> betsDataDfEnrichedWithUserInfo = enrichBetsDataWithUserInfo(betsDataDf);
        Dataset<Row> statisticDf = calculateStatistic(betsDataDfEnrichedWithUserInfo);
        return statisticDf.as(Encoders.bean(StatisticReport.class)).collectAsList();
    }

    @Override
    public List<StatisticReport> statisticCalculate(Instant from, Instant to, String gameName) {
        Dataset<Row> betsDataDf = getBetsData(from, to);
        Dataset<Row> filteredByGameBetsDataDf = filterEventsByGameName(betsDataDf, gameName);
        Dataset<Row> betsDataDfEnrichedWithUserInfo = enrichBetsDataWithUserInfo(filteredByGameBetsDataDf);
        Dataset<Row> statisticDf = calculateStatistic(betsDataDfEnrichedWithUserInfo);
        return statisticDf.as(Encoders.bean(StatisticReport.class)).collectAsList();
    }

    private Dataset<Row> getBetsData(Instant from, Instant to) {
        return betSparkRepository
                .readBetEvent()
                .filter(col("eventTime").$greater(lit(from)))
                .filter(col("eventTime").$less(lit(to)));
    }

    private Dataset<Row> getBetsDataByGameName(Instant from, Instant to, String gameName) {
        return filterEventsByGameName(getBetsData(from, to), gameName);
    }

    private Dataset<Row> filterEventsByGameName(Dataset<Row> eventsDf, String gameName) {
        return eventsDf.filter(
                col("gameName").equalTo(gameName)
                        .or(
                                col("gameName").equalTo(gameName + "-demo")
                        )
        );
    }

    private Dataset<Row> enrichBetsDataWithUserInfo(Dataset<Row> betsDf){
        List<User> users = userRepository.findAll();
        Dataset<Row> usersDF = sparkSession.createDataFrame(users, User.class);
        return betsDf
                .join(usersDF, betsDf.col("userId").equalTo(usersDF.col("id")))
                .drop("id");
    }

    private Dataset<Row> calculateStatistic(Dataset<Row> input) {
        return input
                .withColumn("win", when(col("eventCurrencyCode").equalTo(Currencies.EUR.toString()), col("win").divide(1.1)).otherwise(col("win")))
                .withColumn("bet", when(col("eventCurrencyCode").equalTo(Currencies.EUR.toString()), col("bet").divide(1.1)).otherwise(col("bet")))
                .filter(not(col("countryOfOrigin").equalTo(Country.USA.toString()).and(col("gameName").contains("-demo"))))
                .withColumn("profit", col("win").minus(col("bet")))
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
    }
}
