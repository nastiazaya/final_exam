package com.example.final_exam.service;

import antlr.ASTNULLType;
import com.example.final_exam.model.jpa.User;
import com.example.final_exam.repository.UserRepository;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class BetsEnricherComponentImpl implements BetsEnricherComponent {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SparkSession sparkSession;

    @Override
    public Dataset<Row> enrichBetsDataWithUserInfo(Dataset<Row> betsDf) {
        List<User> users = userRepository.findAll();
        Dataset<Row> usersDF = sparkSession.createDataFrame(users, User.class);
        return betsDf
                .join(usersDF, betsDf.col("userId").equalTo(usersDF.col("id")))
                .drop("id");
    }
}
