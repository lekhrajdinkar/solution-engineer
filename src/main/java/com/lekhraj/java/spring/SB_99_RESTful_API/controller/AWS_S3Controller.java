package com.lekhraj.java.spring.SB_99_RESTful_API.controller;

import com.lekhraj.java.spring.SB_99_RESTful_API.service.AWS_S3Service;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "AWS services", description = "AWS services")
public class AWS_S3Controller {

    @Autowired
    private AWS_S3Service s3Service;

    @GetMapping("/read-s3")
    public String readFromS3(@RequestParam String bucket, @RequestParam String key) {
        return s3Service.readObject(bucket, key);
    }
}
