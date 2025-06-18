package com.lekhraj.java.spring.Spring_02_Core.bean;

import com.lekhraj.java.spring.util.Print;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

// here Bean is created with Default contructor with nothing in inject.
// tried Autowire but failed below.
// tried Constructor inject, but failed with same reason.
// Final Solution - @Bean
@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MyGenericBean<PT,CT extends Item> {
    // @Autowired // failed - but 96 were found:
    PT primitiveType;
    // @Autowired // failed - but 96 were found:
    CT referenceType;

    //Single Contrutor - IAC will call this to inject D
    public MyGenericBean(PT p, CT c){
        Print.print("MyGenericBean :: Constructor(PT p, CT c)  ");
        this.primitiveType = p;
        this.referenceType = c;
    }
    MyGenericBean(PT p){
        Print.print("MyGenericBean :: Constructor(PT p)  ");
        this.primitiveType = p;
    }

    public Long getItemCode(){
        return referenceType.getCode();
    }

    public boolean getPrimitiveTypeInstanceOfInt(){
        return primitiveType instanceof Integer ;
    }
}

