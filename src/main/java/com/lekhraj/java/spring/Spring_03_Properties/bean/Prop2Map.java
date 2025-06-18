package com.lekhraj.java.spring.Spring_03_Properties.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "server") // from yml file.
// @Configuration - Note: Explicitly register in Application.java
public class Prop2Map
{
    private Map<String, String> application;
    private Map<String, List<String>> config1;
    private Map<String, Credentials> users;
    private Map<String, List<Credentials>> config2;
}



