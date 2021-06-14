package com.example.final_exam.controller;


import com.example.final_exam.model.spark.StatisticReport;
import com.example.final_exam.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<List<StatisticReport>> gameStatistics(@RequestParam Instant from,
                                                                @RequestParam Instant to,
                                                                @RequestParam(required = false) Optional<String> gameName){
        List<StatisticReport> suspiciousActivityReport = gameName
                .map(game -> statisticService.statisticCalculate(from, to, game))
                .orElse(statisticService.statisticCalculate(from, to));
        //List<StatisticReport> suspiciousActivityReport = statisticService.statisticCalculate(from, to);
        return ResponseEntity.ok(suspiciousActivityReport);
    }
}
