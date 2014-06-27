package com.securityps.demo.hsts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter 
{
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.headers()
            // .contentTypeOptions()
            // .xssProtection()
            // .cacheControl()
            // .frameOptions()
            .httpStrictTransportSecurity();
    }
}
