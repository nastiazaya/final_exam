package com.example.final_exam.model.spark;

import lombok.Data;

@Data
public class StatisticReport {
    private String gameName;
    private double averageBet;
    private double maxBet;
    private double minBet;
    private double averageWin;
    private double maxWin;
    private double minWin;
    private double averageProfit;
    private double maxProfit;
    private double minProfit;
}
