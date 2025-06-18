package com.lekhraj.java.spring.Spring_03_Properties.converter;

import com.lekhraj.java.spring.Spring_03_Properties.bean.Credentials;
import com.lekhraj.java.spring.util.Print;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class CustomConverter_1 implements Converter<String, Credentials> { // generic : from,to
    @Override
    public Credentials convert(String source) {

       // return "---Converted value From CustomConverter_1--- ";
        String[] c = source.split(",");
        Print.print("-- CustomConverter_1 --", source, c[0], c[1]);
        return  c!=null && c.length == 2
                ? Credentials.builder().username(c[0]).password(c[1]).build()
                : Credentials.builder().build();
    }
}
