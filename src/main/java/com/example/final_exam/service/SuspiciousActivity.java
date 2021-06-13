package com.example.final_exam.service;


import com.example.final_exam.model.Currencies;
import com.example.final_exam.repository.BetRepository;
//import com.example.final_exam.repository.UserRepository;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import static org.apache.spark.sql.functions.*;
import java.time.LocalDateTime;

@Service
public class SuspiciousActivity {

    @Autowired
    private SparkSession sparkSession;

    /*@Autowired
    private UserRepository userRepository;*/

    @Autowired
    private BetRepository betRepository;


    public DataFrame findSuspiciousActivitiesForATimePeriod(LocalDateTime from, LocalDateTime to){
        DataFrame inputDF = betRepository.readBetEvent();

        DataFrame filter = inputDF.withColumn("win", when(col("currencyCode").equalTo(Currencies.EUR.toString()), col("win").divide(1.1)))
                .withColumn("bet", when(col("currencyCode").equalTo(Currencies.EUR.toString()), col("bet").divide(1.1)));
                /*.filter(col("eventTime").compareTo(from) > 0)
                .filter(col("eventTime").compareTo(to) < 0);*/
        return filter;



    }





}
