package com.lekhraj.java.spring.SB_01_AutoConfiguration.CustomConfiguration;

import com.lekhraj.java.spring.SB_01_AutoConfiguration.CustomCondition.MyComplexCondition1;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "sb.customize.configuration.feature1", havingValue = "enabled")
public class MyCustomSBConfig {
    // Configuration beans and methods for the feature1
}

@Configuration
@Conditional(MyComplexCondition1.class)
class MyCustomSBConfig2 {
    // Configuration beans and methods for the feature2
}
