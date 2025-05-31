package com.lekhraj.java.spring.SB_99_RESTful_API.controller;

import com.lekhraj.java.spring.SB_99_RESTful_API.service.OAuth2TokenServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/security")
public class SecurityController
{
    @Autowired OAuth2TokenServiceImpl OAuth2Srv;
    @GetMapping("/admin/secured-api-1")
    public String m1() { return "ADMIN :: secured-api-1";}

    @GetMapping("/user/secured-api-2")
    public String m2() { return "USER :: secured-api-2";}

    @GetMapping("/no-auth-api")
    public ResponseEntity<String> m3() {
          ResponseEntity httpResponse2 = ResponseEntity
                .status(302)
                .header("location", "https://auth0.com/intro-to-iam/what-is-oauth-2")
                .body("redirect");
        return httpResponse2;
    }

    @GetMapping("/oauth/resource/api-1")
    public String m4(@RequestHeader("Authorization") String h1) {
        //log.info("Hello subject {}", jwt.getClaims().get("sub"));
        log.info("header :: Authorization {}", h1);
        return " processed :: /oauth/resource/api-1";
    }

    @GetMapping("/getAccessToken")
    public String m5() {return OAuth2Srv.getAccessToken();}

}

// Role : Admin, user
