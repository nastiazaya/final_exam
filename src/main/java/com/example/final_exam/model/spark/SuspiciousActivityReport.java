package com.example.final_exam.model.spark;

import lombok.Data;

@Data
public class SuspiciousActivityReport {

    private long userId;
    private String email;
    private String name;
    private String lastName;
    private String countryOfOrigin;
    private long betsMadeFromCountriesCount;
    private double win;
    private double bet;
    private long onlineTimeSecs;


}
