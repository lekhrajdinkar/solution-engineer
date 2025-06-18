package com.lekhraj.java.spring.util;

import java.util.Arrays;

public class Print {
    static int count = 0;
    public static void print(Object... o){
        Arrays.stream(o)
                .peek(x-> count++)
                //.map(x->x+"\n^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^")
                .forEach(System.out::println);
        System.out.println("----------------------------------------------");
    }
}
