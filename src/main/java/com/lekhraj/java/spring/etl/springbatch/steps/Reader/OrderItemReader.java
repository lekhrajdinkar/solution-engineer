package com.lekhraj.java.spring.etl.springbatch.steps.Reader;


import com.lekhraj.java.spring.etl.springbatch.entity.Order;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Component
public class OrderItemReader implements ItemReader<Order> {

    private int count = 0;

    @Override
    public Order read() throws Exception {
        // Simulate reading orders (Replace with actual implementation)
        if (count < 5) {
            count++;
            return new Order("Order-" + count, "product-name-" + count, count * 10, true);
        }
        return null; // No more data
    }
}

