package com.lekhraj.java.spring.SB_99_RESTful_API.Runner;

import com.lekhraj.java.spring.SB_99_RESTful_API.service.OAuth2TokenServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

// @Component                     // <<<< here
@Slf4j
public class RestConsumeRunner implements CommandLineRunner {
    String url1 = "http://localhost:8083/spring/security/admin/secured-api-1";
    String url2  = "http://localhost:8083/spring/security/user/secured-api-2";
    String url3  = "http://localhost:8083/spring/security/oauth/resource/api-1";
    @Autowired RestTemplate rest;
    //@Autowired ClientHttpRequestInterceptor basicAuthInterceptor;

    @Autowired
    OAuth2TokenServiceImpl OAuth2srv;

    @Override
    public void run(String... args) throws Exception
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("header-1", "header-1-value");
        headers.add("header-2", "header-2-value");

        // CHECK :: RestConsumeConfig >> ClientHttpRequestInterceptor

        // headers.setContentType(MediaType.APPLICATION_JSON);

        // Path parameters
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put("id", "123");

        // Request parameters
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("param1", "value1");
        queryParams.put("param2", "value2");

        // ===============================

        // call-1
        String result = rest.getForObject(url1, String.class);
        log.info("\nREST-call-1 \nurl : {}, \nresponse : {}",url1,result);

        // call-2
        HttpEntity<String> httpEntity_ForGet = new HttpEntity<>(headers);
        ResponseEntity<String> response = rest.exchange(url1, HttpMethod.GET, httpEntity_ForGet, String.class, pathParams, queryParams);
        log.info("\nREST-call-2 \nurl : {}, \nresponse : {}",url1,response);

        // HttpEntity<String> httpEntity_ForPosT = new HttpEntity<>("{\"json-key-1\":\"json-value-1\"}", headers);
        // ResponseEntity<String> response2 = rest.exchange(url1, HttpMethod.POST, httpEntity_ForGet, String.class, pathParams, queryParams);
        // log.info("\nREST-call-3 \nurl : {}, \nresponse : {}",url1,result);


        // call-3 | OAuth2 client
        //headers.add("Authorization", OAuth2srv.getAccessToken());
        httpEntity_ForGet = new HttpEntity<>(headers);
        response = rest.exchange(url3, HttpMethod.GET, httpEntity_ForGet, String.class, pathParams, queryParams);
        log.info("\nREST-call-3 \nurl : {}, \nresponse : {}",url3, response);
    }
}
