package com.lekhraj.java.spring.Spring_02_Core.configuration;

import com.lekhraj.java.spring.Spring_02_Core.bean.ItemImpl_2;
import com.lekhraj.java.spring.Spring_02_Core.bean.MyGenericBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IoCcontract_3_gbean
{
    // 1 generic bean eg
    // 1.1
    // @Bean MyGenericBean gbean_1(){ return new MyGenericBean<Integer, ItemImpl_2>();}
     ItemImpl_2 item2; // <<< Notice, wired in config file...


    IoCcontract_3_gbean(@Autowired ItemImpl_2 item2FromIOC3){
        this.item2 = item2FromIOC3;
    }

    /*IoCcontract_3_gbean( ItemImpl_2 item2){
        this.item2 = item2;
    }*/

    //1.2.2.
    @Bean MyGenericBean gbean_1(){
        return new MyGenericBean<Integer, ItemImpl_2>(100, item2);
    }

    @Bean
    public static ItemImpl_2 item2FromIOC3(){
        System.out.println("IoCcontract_3_Gbean : Bean Created, type-Item>ItemImpl_2, name-item2FromIOC3");
        return  new ItemImpl_2();
    }


}
