package com.lekhraj.java.spring.Spring_03_Properties;

import com.lekhraj.java.spring.Spring_03_Properties.bean.ConfigurationPropertiesByPrefixBean;
import com.lekhraj.java.spring.Spring_03_Properties.bean.DatabasePropertiesMap;
import com.lekhraj.java.spring.Spring_03_Properties.bean.RabbitPropertiesMap;
import com.lekhraj.java.spring.Spring_03_Properties.bean.Prop2Map;
import com.lekhraj.java.spring.util.Print;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value="runner_2")
public class Runner2 implements CommandLineRunner {
    static Logger log = LoggerFactory.getLogger(Runner2.class);

    @Autowired
    DatabasePropertiesMap dbmap;
    @Autowired
    RabbitPropertiesMap rabbitMap;

    @Autowired
    Environment env; //preferred.


    // ==== SpEL ==== #{}
    @Value("#{${valuesMap}}")
    Map<String, Integer> valuesMap;
    @Value("#{'${listOfValues}'.split(',')}")
    private String[] valuesArray;
    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Autowired Prop2Map myMapsFromProprty;
    @Autowired ConfigurationPropertiesByPrefixBean cp;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n\n============= runner_2 (Spring properties) ============== START");

        log.info("\n --- DATABASE CONFIG MAP ---\n{}, \n --- RABBIT CONFIG MAP ---\n{}",dbmap, rabbitMap );
        log.info("ENV API :: db.url - {}",env.getProperty("db.url"));

        // Spel
        log.info("valuesMap : {}", valuesMap);
        log.info("valuesArray : {} {} {}",valuesArray); //has 3 values

        //Print Active Profiles
        log.info("activeProfile : {}", activeProfile);
        Arrays.stream(env.getActiveProfiles()).forEach(System.out::println);

        Print.print(myMapsFromProprty);
        Map configmap = myMapsFromProprty.getConfig1();
        Map applicationmap = myMapsFromProprty.getApplication();
        Map usermap = myMapsFromProprty.getUsers();
        /*configmap.forEach((k,v)->{Print.print(k,v);});
        applicationmap.forEach((k,v)->{Print.print(k,v);});
        usermap.forEach((k,v)->{Print.print(k,v);});*/

        Print.print("onfigurationPropertiesByPrefixBean , prefix-mail : ",cp);

        System.out.println("\n============= runner_2 (Spring properties) ============== END\n\n");

    }
}

/*
ref: https://chat.openai.com/c/e3baac20-b7a8-4cf8-8ffe-b84c8d83cfc0

Notes:

1. When the same property is defined in multiple property files in a Spring Boot project,
Spring resolves the value based on the order in which the property sources are loaded.

2. property outside jar::
export SPRING_CONFIG_LOCATION=/path/to/your/config/myprops.properties
java -jar your-application.jar

2. Load order:
    - Default properties
    - Profile-specific properties:
    - Additional property sources: You can use the @PropertySource.
    - Environment variables and command-line arguments:
 */
