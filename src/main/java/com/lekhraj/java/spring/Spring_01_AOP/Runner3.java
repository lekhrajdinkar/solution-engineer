package com.lekhraj.java.spring.Spring_01_AOP;

import com.lekhraj.java.spring.Spring_01_AOP.annotation.MyAopMetric;
import com.lekhraj.java.spring.Spring_02_Core.Runner1;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
// @Order(3)
public class Runner3 implements CommandLineRunner
{

    static Logger log = LoggerFactory.getLogger(Runner1.class);

    @SneakyThrows  //@MyAopMetric
    public void task1(){
        Thread.sleep(2000);
    }

    @SneakyThrows  //@MyAopMetric
    public void task2(){
        Thread.sleep(3000);
    }

    @Override @MyAopMetric
    public void run(String... args) throws Exception {
        System.out.println("\n\n============= runner_3_AOP  ============== START");
        task1();
        task2();
        System.out.println("\n\n============= runner_3_AOP  ============== END");
    }


}
