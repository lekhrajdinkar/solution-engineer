package com.lekhraj.java.spring.etl.springbatch.steps.processer;


import com.lekhraj.java.spring.etl.springbatch.entity.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

//============== UPER CASE ==========
@Component("user_processor_1_uppercase")
public class UserItemProcessor implements ItemProcessor<User, User>
{
    @Override
    public User process(User user) {
        user.setFirstName(user.getFirstName().toUpperCase());
        user.setLastName(user.getLastName().toUpperCase());
        return user;
    }
}

