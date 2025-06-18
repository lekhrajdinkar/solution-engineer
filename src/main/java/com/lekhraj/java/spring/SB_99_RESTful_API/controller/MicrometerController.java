package com.lekhraj.java.spring.SB_99_RESTful_API.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MicrometerController {
    private final Counter testCounter;

    public MicrometerController(MeterRegistry registry) {
        this.testCounter = Counter.builder("test.counter").register(registry);
    }

    @GetMapping("/micrometer/test-1")
    public String test() {
        testCounter.increment();
        return "Test";
    }
}
