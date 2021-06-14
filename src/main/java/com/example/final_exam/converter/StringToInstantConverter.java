package com.example.final_exam.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class StringToInstantConverter implements Converter<String, Instant> {
    @Override
    public Instant convert(String source) {
        //DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return LocalDateTime.parse(source, formatter).toInstant(ZoneOffset.UTC);
    }
}
