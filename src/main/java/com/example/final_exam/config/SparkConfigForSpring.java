package com.example.final_exam.config;


import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfigForSpring {

    @Bean
    public SparkSession sparkSessionDev(){
        return SparkSession.builder().master("local[*]").appName("finalExam").getOrCreate();
    }

}
