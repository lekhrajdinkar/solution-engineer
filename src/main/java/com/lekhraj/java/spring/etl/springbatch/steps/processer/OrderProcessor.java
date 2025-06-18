package com.lekhraj.java.spring.etl.springbatch.steps.processer;


import com.lekhraj.java.spring.etl.springbatch.entity.Order;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class OrderProcessor implements ItemProcessor<Order, Order> {

    @Override
    public Order process(Order order) throws Exception {
        // Apply business logic to process the order
        order.setProcessed(true);
        return order;
    }
}

