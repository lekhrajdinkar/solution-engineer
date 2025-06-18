package com.lekhraj.java.spring.SB_99_RESTful_API.configuration;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// WebMvcConfigurer / Cusomize Spring MVC : Cors
// WebServerFactoryCustomizer / fi -> Cusomise Tomcat
// WebApplicationInitializer / SC

@Configuration
public class WebConfig implements WebApplicationInitializer {

    @Bean // for spring MVC
    WebMvcConfigurer webMvcConfigurerForApp(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("*");
            }
        };
    }

    @Bean
    WebServerFactoryCustomizer webServerfactory() // FunctionalInterface
    {
        return new WebServerFactoryCustomizer<TomcatServletWebServerFactory>(){
            @Override
            public void customize(TomcatServletWebServerFactory factory) {
                factory.setPort(8083);  // Change the port to 8081
                factory.setContextPath("/spring");  // Set the context path
            }
        };
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("AppName", "SB_99_RESTful_API");
        // register servlet,filter,webListener
    }
}

//@EnableWebMvc
//@ComponentScan(basePackages = "com.lekhraj.java.spring.SB_99_RESTful_API.configuration.controller")
