package com.example.final_exam.model;


import java.time.Instant;

public class BetEvent {

    private long eventId;
    private Instant eventTime;
    private Country country;
    private Currencies currencyCode;
    private long userId;
    private float bet;
    private Game gameName;
    private float win;
    private long onlineTimeSecs;
}
