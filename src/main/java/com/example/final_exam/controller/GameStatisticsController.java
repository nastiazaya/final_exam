package com.example.final_exam.controller;


import com.example.final_exam.model.BetEvent;
import com.example.final_exam.service.StatisticService;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = {"/games-statistics"}, produces = APPLICATION_JSON_VALUE)
public class GameStatisticsController {

    @Autowired
    private StatisticService statisticService;

   /* @GetMapping
    public String gameStatistics(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
                                 @RequestParam(required = false) String gameName){
       statisticService.statisticCalculate(from.toInstant(ZoneOffset.UTC), to.toInstant(ZoneOffset.UTC));
        return "ResponseEntity.";
    }*/

    @GetMapping
    public String gameStatistics(@RequestParam Instant from,
                                 @RequestParam Instant to,
                                 @RequestParam(required = false) String gameName){
        statisticService.statisticCalculate(from, to);
        return "ResponseEntity.";
    }
}
