package com.example.final_exam.converter;

import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.stereotype.Component;

//@Component
public class TimeConversionComponent extends GenericConversionService {
    public TimeConversionComponent() {
        addConverter(new StringToInstantConverter());
    }
}
