package com.lekhraj.java.spring.SB_99_RESTful_API.hibernate_converter;

import jakarta.persistence.AttributeConverter;

import java.util.Base64;

public class PasswordConverter implements AttributeConverter<String,String> {
    @Override
    public String convertToDatabaseColumn(String s) {
        return Base64.getEncoder().encodeToString(s.getBytes());
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return new String(Base64.getDecoder().decode(s));
    }
}

//Apply on entity field
//@Convert(covert="PasswordConverter")