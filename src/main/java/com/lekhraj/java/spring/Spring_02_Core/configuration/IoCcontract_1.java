package com.lekhraj.java.spring.Spring_02_Core.configuration;

import com.lekhraj.java.spring.Spring_02_Core.bean.Item;
import com.lekhraj.java.spring.Spring_02_Core.bean.ItemImpl_1;
import com.lekhraj.java.spring.Spring_02_Core.bean.Store;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;

/*
A. Container -
@Bean, @Component // each has name and type. //DefaultScope - Singleton(early-loaded)

B. DI
- manual0 - Setter + Constructor (for mandatory dependency)
- For additional dependency : Auto ( @Autowited - apply at 3 places) - Automatically resolves >> type >> found 2+ >> @Primary >> @Qualifier("beanName")
 */

@Configuration
@ComponentScan("com.lekhraj.java.spring.SpringCore.bean") //perform a context scan for additional beans.
//@ComponentScan(basePackages = "pkg-1,pkg-2")
//@ComponentScan(basePackageClasses = "c1.class, c2.class")
public class IoCcontract_1 {
    @Bean()
    // @Bean(Autowire=Autowire.BY_TYPE)
    // deprecated as of Spring 5.1.
    // "Autowire attribute" - to resolve additional dependencies.
    public static Store store1() // Notice : static
    {
        //A. --- Setter injection ---
        Store bean = new Store(); //default Constructor
        //bean.setItem1(item1());
        //bean.setItem1(IoCcontract_2.item2());
        System.out.println("IoCcontract_1 : Bean Created, Type-Store, name-store1, hashcode-"+bean.hashCode() );

        //B. --- Constructor injection --- Preferred
        // we pass the required components into a class at the time of instantiation.
        // bean = new Store(item1());
        return bean;
    }

    @Bean
    @Order()
    //@Primary
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) // default
    public static Item item1(){ // Type : Item, name: item1
        Item bean = new ItemImpl_1();
        System.out.println("IoCcontract_1 : Bean Created, type-Item, name-item1, hascode-"+bean.hashCode());
        return  bean;
    }

    @Bean
    public static Item item1_again(){ // Type : Item, name: item1_againß
        Item bean = new ItemImpl_1();
        System.out.println("IoCcontract_1 : Bean Created, type-Item, name-item1_again, hascode-"+bean.hashCode());
        return  bean;
    }
}

/*
@Bean(Autowire=Autowire.BY_TYPE)
"Autowire attribute" - to resolve additional dependencies.
@Autowired types::
=================
1. no:
------
the default value – this means no autowiring is used for the bean
and we have to explicitly name the dependencies.

2. byName:
---------
autowiring is done based on the name of the property,
therefore Spring will look for a bean with the same name
as the property that needs to be set.

3. byType:
---------
similar to the byName autowiring, only based on the type of the property.
This means Spring will look for a bean with the same type of the property to set.
If there’s more than one bean of that type, the framework throws an exception.

4. constructor: <<<<
---------------
autowiring is done based on constructor arguments, meaning Spring will
look for beans with the same type as the constructor arguments.
 */

