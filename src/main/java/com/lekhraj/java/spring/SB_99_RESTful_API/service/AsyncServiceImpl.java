package com.lekhraj.java.spring.SB_99_RESTful_API.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncServiceImpl {

    @Async ("taskExecutor-1")
    public CompletableFuture<String> performTask(String input) {
        // Simulate long-running task
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return CompletableFuture.completedFuture("Processed: " + input);
    }
}