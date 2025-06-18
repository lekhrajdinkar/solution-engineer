package com.lekhraj.java.spring.Spring_02_Core.bean;


import lombok.ToString;

@ToString
public class ItemImpl_1 implements Item{
    String barcode = "barcode_1";
    @Override
    public String getDetail() {
        return "Nice product for hair";
    }
    @Override
    public String getName() {
        return "GK-1";
    }

    @Override
    public Long getCode() {
        return 1L;
    }

    @Override
    public boolean isActive() {
        return true;
    }
}
