package com.lekhraj.java.spring.Spring_02_Core.bean;


import lombok.ToString;

@ToString
public class ItemImpl_2 implements Item{
    String barcode = "barcode_2";
    @Override
    public String getDetail() {
        return "Nice product for skin";
    }
    @Override
    public String getName() {
        return "hempz";
    }

    @Override
    public Long getCode() {
        return 2L;
    }

    @Override
    public boolean isActive() {
        return true;
    }
}
