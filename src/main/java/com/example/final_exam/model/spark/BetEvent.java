package com.example.final_exam.model.spark;


import lombok.Data;

import java.time.Instant;
@Data
public class BetEvent {
    private long eventId;
    private Instant eventTime;
    private String eventCountry;
    private String eventCurrencyCode;
    private long userId;
    private float bet;
    private String gameName;
    private float win;
    private long onlineTimeSecs;
}
