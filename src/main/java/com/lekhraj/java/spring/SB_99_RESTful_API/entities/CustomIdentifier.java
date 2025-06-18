package com.lekhraj.java.spring.SB_99_RESTful_API.entities;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.util.Random;
import java.util.UUID;

public class CustomIdentifier implements IdentifierGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        return UUID.randomUUID();
    }
}
