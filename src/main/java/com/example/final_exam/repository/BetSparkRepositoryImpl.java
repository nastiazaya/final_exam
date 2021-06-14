package com.example.final_exam.repository;

import com.example.final_exam.model.BetEvent;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BetSparkRepositoryImpl implements BetSparkRepository {

    @Autowired
    private SparkSession sparkSession;

    @Override
    public DataFrame readBetEvent(){
        StructType schema = Encoders.bean(BetEvent.class).schema();
        DataFrame inputDf = sparkSession.read().option("multiline", "true").schema(schema).json("data/generated_event.json");
        return inputDf;
    }

}