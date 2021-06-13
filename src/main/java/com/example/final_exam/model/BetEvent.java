package com.example.final_exam.model;


import java.time.LocalDateTime;

public class BetEvent {

    private long eventId;
    private LocalDateTime eventTime;
    private Country country;
    private Currencies currencyCode;
    private long userId;
    private float bet;
    private Game gameName;
    private float win;
    private long onlineTimeSecs;
}
