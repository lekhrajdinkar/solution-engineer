package com.lekhraj.java.spring.Spring_02_Core;

import com.lekhraj.java.spring.Spring_02_Core.Annotation.MyAnnotation;
import com.lekhraj.java.spring.Spring_02_Core.bean.Item;
import com.lekhraj.java.spring.Spring_02_Core.bean.SingletonBean;
import com.lekhraj.java.spring.Spring_02_Core.bean.Store;
import com.lekhraj.java.spring.util.Print;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component(value = "Runner1")
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) // default
public class Runner1 implements CommandLineRunner {
    static Logger log = LoggerFactory.getLogger(Runner1.class);

    //========================================
    // Step-1 :: Load bean with default contructor
    // Step-2 :: Resolve dependencies - Manual or Auto.
    // or
    // Step-1/2 :: Load bean with argument-contructor, which injects while creation
    // This is preferred, it keeps object Immutable.
    //
    // 1. DI(Manual) - Injection by Construtor
    // 2. DI(Manual) - Injection by Setter
    // 3. DI(Auto) - Type >> if 2+ >> @primary >> @Qualifier("beanName")
    //========================================

    @Autowired(required = true) Store store; // 3.1 DI - Auto (feild injection uses reflection)

    Runner1(){
        log.info("Main :: default construtor");
    }

    //@Autowired(required = true) // 3.2 DI - Auto
    Runner1(Store store){
        log.info("SpringIoCAndDI :: construtor(Store) :: store:{}", store);
        this.store = store;
        this.store.setStoreName("target Super market");
    }

    //@Autowired(required = true)  // 3.3 DI - Auto
    public void setStore(Store store){
        log.info("SpringIoCAndDI :: setStore :: store:{}", store);
        this.store = store;
        this.store.setStoreName("target Super market");
    }

    public void loadIaC(Class configClass){
        log.info("\n\n======= Loading Another Container  with IoCcontract_1 config only =======");
        ApplicationContext context = new AnnotationConfigApplicationContext(configClass);

        Item itemByName = (Item) context.getBean("item1");
        log.info(String.valueOf(itemByName.hashCode()));

        Item itemByType = (Item) context.getBean(Item.class); //NoUniqueBeanDefinitionException - use @primary
        log.info(String.valueOf(itemByType.hashCode()));
    }

    // ==== Custom Annotation ====
    @MyAnnotation(value = "newValue", count = 100) public void testMethod1(){}
    @SneakyThrows
    public void CustomAnnotationTest(){
        Method method = Runner1.class.getMethod("testMethod1");
        MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);

        if (annotation != null) {
            System.out.println("Value: " + annotation.value());
            System.out.println("Count: " + annotation.count());
        }
    }

    // ======= Runner ========  <<<<<

    @Autowired SingletonBean sbean;

    @Override
    public void run(String... args) throws Exception
    {
        System.out.println("\n\n============= runner_1 (Spring Core - DI, lifeCycle, etc) ============== START");
        // A. DI
        Print.print(" -- A. DI -- ", store);
        Print.print(" -- A. DI -- ", store.getItem().hashCode());

        // B. DI -Advance - inject Collection with order, etc
        Print.print( "-- B. Advance DI - Injecting Collection ");
        store.printItems(); // collection

        // C. DI -Advance - Inject generic bean
        Print.print("-- C. Advance DI - Injecting Generic bean -1 (@Bean) ", store.getGbean1() );
        Print.print("-- C. Advance DI - Injecting Generic bean -2 (@Component)", store.getGbean2() );
        Print.print("-- D. Advance DI - proto into Singleton (Did not work): ",
                "proto hashcode : ", sbean.getPbean().hashCode(),
                "proto hashcode : ", sbean.getPbean().hashCode());

        // 99. CustomAnnotationTest();

        // 100. loadIaC(IoCcontract_1.class); //  <<<<<<< comment/uncomment
        System.out.println("============= runner_1 (Spring Core - DI, lifeCycle, etc) ============== END\n\n");
    }
}

