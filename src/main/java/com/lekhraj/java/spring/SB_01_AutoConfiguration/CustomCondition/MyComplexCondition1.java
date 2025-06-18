package com.lekhraj.java.spring.SB_01_AutoConfiguration.CustomCondition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

// @Component  <<< NOT NEEDED, Pls remove. explaination below.
public class MyComplexCondition1 implements Condition
{
    // Did not work
    // @Value("${sb.customize.configuration.feature2}")
    String feature2Flag;

    // Did not work
    // @Autowired
    Environment env;
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        this.env = context.getEnvironment(); // manually inject
        //this.feature2Flag = env.getProperty("sb.customize.configuration.feature2");
        return feature2Flag!=null && feature2Flag.equals("enabled") ? true : false;
        //return true;
    }
}

/*
No, you don't need to annotate MyComplexCondition1 with @Component for it to be recognized as a bean by Spring.
In the context of conditional configuration, Spring doesn't treat Condition implementations as regular beans
 that are created by component scanning or other bean creation mechanisms.
 Instead, Spring uses them internally to determine whether a configuration class or bean should be created
 based on the conditions specified.

When you use @Conditional with a Condition implementation like MyComplexCondition1,
Spring will instantiate and use this condition during the application context initialization process
to decide whether to include the annotated configuration class or bean in the application context.
MyComposedCondition itself doesn't need to be a Spring-managed bean;
it's a part of Spring's internal mechanism for conditional configuration.
 */
