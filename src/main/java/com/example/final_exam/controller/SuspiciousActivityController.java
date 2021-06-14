package com.example.final_exam.controller;


import org.apache.spark.sql.Row;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = {"/suspicious-activities"}, produces = APPLICATION_JSON_VALUE)
public class SuspiciousActivityController {

    @GetMapping
    public String suspiciousActivityForATimePeriod(@RequestParam Instant from,
                                                                      @RequestParam Instant to){
        return "";
    }


}




