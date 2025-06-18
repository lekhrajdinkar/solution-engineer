package com.lekhraj.java.spring.SB_99_RESTful_API.Runner;

import com.lekhraj.java.spring.SB_99_RESTful_API.service.OAuth2TokenServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

//@Component
@Slf4j
public class RestConsumeRunner_2 implements CommandLineRunner
{
    String url1 = "http://localhost:8083/spring/security/oauth/resource/api-1";
    @Autowired RestTemplate rest;
    @Autowired    OAuth2TokenServiceImpl OAuth2srv;

    String token = "eyJraWQiOiIybGUyLS1Lbml3WGgzMFF4N0EybFplRk5BRlhFWDBhb0RhRk5QTjZHV2dzIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULnRCYzNIN2o2NmtyeDctTzMwMHZhQUkxdHlvSWJIcFdvbDcyUklld0RvSkUiLCJpc3MiOiJodHRwczovL2Rldi0xNjIwNjA0MS5va3RhLmNvbS9vYXV0aDIvYXVzbGRieGxmYWtid3EzMlA1ZDciLCJhdWQiOiIwb2FsZGJrN3lzOHB4NDFHeTVkNyIsImlhdCI6MTczMjQxMTEzOCwiZXhwIjoxNzMyNDE0NzM4LCJjaWQiOiIwb2FsZGJrN3lzOHB4NDFHeTVkNyIsInNjcCI6WyJhcHBfcmVhZF9sZWtocmFqIl0sInN1YiI6IjBvYWxkYms3eXM4cHg0MUd5NWQ3In0.Un_nsyivaXQiHU0xOIBiMuAY1LwIN8bLweXmbPnmETJHIu_09ux_HTe5e6rQ54lxSoc-cKocOJhzKS1PdGBgQ7s9BB0zBYItBq-ZjkXXFEu-LQn5eRzTOSsk-WuJ7ucbxpz-51othMjFGqn_8kZsksMojcdVOIzwbViFeA8oSySwadsR52kC_Iw-w29atVGsnx9qfCTOTHp5577YmPoSE7BkPG8UQFLWaCU1irzJIH8NWs6xWVZjJWxwdCTkkyaCBDCWCZnjhDlfaxMtuIoR-aL2WStxGwWaTxeIfr9iW4AY_7tG5TccL42St8p581rPCYNIHuIckN3CgxnrVG4FwA";

    @Override
    public void run(String... args) throws Exception
    {
        HttpHeaders headers = new HttpHeaders();
        //headers.add("Authorization", OAuth2srv.getAccessToken());
        headers.add("Authorization", token);
        HttpEntity<String> httpEntity_ForGet = new HttpEntity<>(headers);
        ResponseEntity<String> response = rest.exchange(url1, HttpMethod.GET, httpEntity_ForGet, String.class);
        log.info("\nREST-call-1 \nurl : {}, \nresponse : {}",url1, response);
    }
}
