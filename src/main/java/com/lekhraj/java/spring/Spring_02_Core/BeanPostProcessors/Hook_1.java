package com.lekhraj.java.spring.Spring_02_Core.BeanPostProcessors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

// Called for each bean.
// register it Application Context <<<
public class Hook_1 implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // Perform some action before initialization
        System.out.println("Before initialization of bean: " + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // Perform some action after initialization
        System.out.println("After initialization of bean: " + beanName);
        return bean;
    }
}

