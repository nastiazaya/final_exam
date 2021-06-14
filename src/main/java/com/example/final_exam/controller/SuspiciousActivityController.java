package com.example.final_exam.controller;


import com.example.final_exam.model.spark.SuspiciousActivityReport;
import com.example.final_exam.service.SuspiciousActivityService;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = {"/suspicious-activities"}, produces = APPLICATION_JSON_VALUE)
public class SuspiciousActivityController {

    @Autowired
    private SuspiciousActivityService suspiciousActivityService;

    @GetMapping
    public ResponseEntity<List<SuspiciousActivityReport>> suspiciousActivityForATimePeriod(@RequestParam Instant from,
                                                                                           @RequestParam Instant to) {
        List<SuspiciousActivityReport> suspiciousActivityReport = suspiciousActivityService.findSuspiciousActivitiesForATimePeriod(from, to);
        return ResponseEntity.ok(suspiciousActivityReport);
    }


}




