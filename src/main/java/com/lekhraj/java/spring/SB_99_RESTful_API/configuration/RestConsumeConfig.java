package com.lekhraj.java.spring.SB_99_RESTful_API.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.Base64;

@Configuration
public class RestConsumeConfig {
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(basicAuthInterceptor2());  /// <<< change here
        return restTemplate;
    }

    // Interceptors
    @Bean("basicAuthInterceptor_user1")
    public ClientHttpRequestInterceptor basicAuthInterceptor1() {
        return new BasicAuthenticationInterceptor("user1", "user1Pass");
    }

    @Bean("basicAuthInterceptor_admin")
    public ClientHttpRequestInterceptor basicAuthInterceptor2() {
        return new BasicAuthenticationInterceptor("admin", "adminPass");
    }

    // @Bean
    public ClientHttpRequestInterceptor bearerTokenInterceptor() {
        return new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                    throws IOException
            {
                String token1 = Base64.getEncoder().encodeToString("user1:user1Pass".getBytes());
                String token2 = Base64.getEncoder().encodeToString("admin:adminPass".getBytes());

                HttpHeaders headers = request.getHeaders();
                headers.add("Authorization", "Bearer "+ token2);
                return execution.execute(request, body);
            }
        };
    }

    /*@Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }*/
}
