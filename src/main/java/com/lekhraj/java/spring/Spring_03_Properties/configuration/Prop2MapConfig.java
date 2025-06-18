package com.lekhraj.java.spring.Spring_03_Properties.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:prop-to-map.yml")
public class Prop2MapConfig {
}


