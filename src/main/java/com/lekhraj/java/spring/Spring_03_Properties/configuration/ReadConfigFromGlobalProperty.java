package com.lekhraj.java.spring.Spring_03_Properties.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources(value = {
        @PropertySource(value = "classpath:global-database-${spring.profiles.active}.properties"),
        @PropertySource(value = "classpath:global-rabbit-mq-${spring.profiles.active}.properties")
        // order matter
})
public class ReadConfigFromGlobalProperty {

    ReadConfigFromGlobalProperty(){
        System.out.println(" ===== RooT of classpath ==== "+getClass().getResource("/").getPath());
    }
}
