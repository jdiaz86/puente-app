package com.puente.puenteapp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private static final String[] PUBLIC_MATCHERS = {"/**/favicon.ico", "/webjars/**", 
													"/images/**", "/js/**", 
													"/css/**", "/login"};


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.csrf().disable()
            .anonymous().disable()
            .authorizeRequests()
            .antMatchers(PUBLIC_MATCHERS).permitAll()
            .anyRequest().authenticated().and().logout();*/
    	http.authorizeRequests().anyRequest().permitAll();
    }
    
}
