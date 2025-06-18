package com.lekhraj.java.spring.SB_99_RESTful_API.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {
    IN_STOCK(1,"in-stock"),
    OUT_OF_STOCK(2, "no-stock"),
    FEW_STOCK(3, "few-stock");

    int code;
    String name;

    @JsonValue
    String getCompleteValue(){
        return name + "( "+code+" )";
    }
}
