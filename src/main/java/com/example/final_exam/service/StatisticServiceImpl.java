package com.example.final_exam.service;

import com.example.final_exam.model.Country;
import com.example.final_exam.model.Currencies;
import com.example.final_exam.repository.BetSparkRepository;
import org.apache.spark.sql.DataFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static org.apache.spark.sql.functions.*;

@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private BetSparkRepository betSparkRepository;

    @Override
    public DataFrame statisticCalculate(Instant from, Instant to){
        DataFrame inputDF = betSparkRepository.readBetEvent();
        DataFrame result = inputDF.withColumn("win", when(col("currencyCode").equalTo(Currencies.EUR.toString()), col("win").divide(1.1)))
                .withColumn("bet", when(col("currencyCode").equalTo(Currencies.EUR.toString()), col("bet").divide(1.1)))
                .withColumn("gameName",when(col("country").equalTo((Country.US.toString())),col("gameName")))
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

        return result;

    }


}
