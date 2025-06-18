package com.lekhraj.java.spring.SB_99_RESTful_API.controller;

import com.lekhraj.java.spring.SB_99_RESTful_API.service.AsyncServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
public class AsyncController {

    @Autowired
    private AsyncServiceImpl asyncService;

    @GetMapping("/async")
    public CompletableFuture<ResponseEntity<String>> asyncEndpoint()
    {
        return asyncService.performTask("data")
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> ResponseEntity.status(500).body("Error occurred"));
    }
}