package com.lekhraj.java.spring.etl.springbatch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class Order
{
    private String orderId;
    private String productName;
    private int quantity;
    private boolean processed;
}

