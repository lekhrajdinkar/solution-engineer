package com.lekhraj.java.spring.Spring_03_Properties.configuration;

import org.springframework.context.annotation.PropertySource;

// database-dev1/2.property

 //@Configuration  // un-comment/Comment  <<<< NOT WORKING...PATH is wrong

@PropertySource("classpath:com/lekhraj/java/spring/SpringProperties/resources/database-${spring.profiles.active}.properties")
public class DatabaseConfig {
}


/*
/Users/lekhrajdinkar/github/02-spring/src/main/java/
com/lekhraj/java/spring/SpringProperties/resources/database-dev1.properties
 */
