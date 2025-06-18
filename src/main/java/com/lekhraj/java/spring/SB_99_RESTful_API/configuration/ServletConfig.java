package com.lekhraj.java.spring.SB_99_RESTful_API.configuration;

import com.lekhraj.java.spring.SB_99_RESTful_API.servlet.MyServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfig {
    // ServletRegistrationBean - generic Type Class
    @Bean
    public ServletRegistrationBean<MyServlet> myServletRegistrationBean() {
        ServletRegistrationBean<MyServlet> registrationBean =new ServletRegistrationBean<>(new MyServlet(), "/custom");
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }

    /*
    ==============================
    Change from Tomcat to jetty.
    ==============================
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jetty</artifactId>
    </dependency>

    @Bean
    public JettyEmbeddedServletContainerFactory  jettyEmbeddedServletContainerFactory() {
        JettyEmbeddedServletContainerFactory jettyContainer = new JettyEmbeddedServletContainerFactory();
        jettyContainer.setPort(9000);
        jettyContainer.setContextPath("/springbootapp");
        return jettyContainer;
    }
    */
}
