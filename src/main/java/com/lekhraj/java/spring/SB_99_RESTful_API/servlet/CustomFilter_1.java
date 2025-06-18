package com.lekhraj.java.spring.SB_99_RESTful_API.servlet;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
@Slf4j
//@Component
public class CustomFilter_1 extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException
    {
        log.info("CustomeFilter_1 :: {}",servletRequest);
        // Authentication authentication = null;
        // authentication = AuthenticationService.getAuthentication((HttpServletRequest) request);
        // SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}

// REGISTER ALSO::
// SecurityFilterChain  >> add filter >> new CustomFilter_1()
