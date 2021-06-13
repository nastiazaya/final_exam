package com.example.final_exam.controller;


import com.example.final_exam.model.BetEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = {"/suspicious-activities"}, produces = APPLICATION_JSON_VALUE)
public class SuspiciousActivityController {

    @GetMapping
    public String suspiciousActivityForATimePeriod(@RequestParam LocalDateTime from,
                                                                     @RequestParam LocalDateTime to){
        return to.toString();
    }


}




