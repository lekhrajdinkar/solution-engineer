package com.lekhraj.java.spring.Spring_02_Core.configuration;

import com.lekhraj.java.spring.Spring_02_Core.bean.Item;
import com.lekhraj.java.spring.Spring_02_Core.bean.ItemImpl_2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan("com.lekhraj.java.spring.SpringCore.bean")
public class IoCcontract_2 {
    // rename to item1() - bean registration will get failed with this name.
    // Because "item1" name is already taken
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Qualifier("IoCcontract_2_item2")
    public static Item item2(){
        Item bean = new ItemImpl_2();
        System.out.println("IoCcontract_2 : Bean Created, type-Item, name-item2, hashcode-"+bean.hashCode());
        return  bean;
    }

    @Bean @Qualifier("IoCcontract_2_allItems")
    public static List<Item> allItems(){
        List<Item> allItem=new ArrayList<>();
        allItem.add(new ItemImpl_2());
        allItem.add(new ItemImpl_2());
        allItem.add(new ItemImpl_2());
        System.out.println("IoCcontract_2 : Bean Created, type-List<Item>, name-allItems, hashcode-"+allItem.hashCode());
        return  allItem;
    }
}
