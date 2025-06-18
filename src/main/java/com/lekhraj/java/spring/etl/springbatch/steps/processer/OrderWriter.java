package com.lekhraj.java.spring.etl.springbatch.steps.processer;


import com.lekhraj.java.spring.etl.springbatch.entity.Order;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderWriter implements ItemWriter<Order> {

    /*@Override
    public void write(List<? extends Order> orders) throws Exception {
        // Simulate writing processed orders (Replace with actual implementation)
        orders.forEach(order -> System.out.println("Writing Order: " + order));
    }*/

    @Override
    public void write(Chunk<? extends Order> chunk) throws Exception {
        chunk.forEach(order -> System.out.println("Writing Order: " + order));
    }
}

