package com.lekhraj.java.spring.Spring_02_Core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;

@Configuration
public class PostProcessorConfig {
    @Bean
    public static PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    // @Bean public Hook_1 hook1() {return new Hook_1();} // <<<< comment/uncomment
}
