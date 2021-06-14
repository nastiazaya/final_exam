package com.example.final_exam.controller;


import com.example.final_exam.model.BetEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = {"/games-statistics"}, produces = APPLICATION_JSON_VALUE)
public class GameStatisticsController {

    @GetMapping
    public ResponseEntity<BetEvent> gameStatistics(@RequestParam LocalDateTime from, @RequestParam LocalDateTime to,
                                                   @RequestParam(required = false) String gameName){
        return ResponseEntity;
    }
}
