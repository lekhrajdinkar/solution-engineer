package com.lekhraj.java.spring.Spring_02_Core.bean;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SingletonBean {
    ProtoypeBean pbean;

    // Everytime call getPbean(), Spring will  create and return new object.
    @Lookup // Method Injection
    public ProtoypeBean getPbean() {
        // return pbean;
        // if not using @lookup, then write custom code to create new object from ApplicationContext.getbean()
        return null; // <<< notice. spring wont return null :)
    }
}
