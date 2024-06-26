package com.buka.config;

import com.buka.filters.AuthFilter;
import com.buka.security.AuthHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthFilter authFilter;
    @Autowired
    private AuthHandler authHandler;
    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;
//    @Autowired
//    AccessDeniedHandler accessDeniedHandler;
    @Value("${swaggerPath}")
    private String[] swaggerPath;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().sessionManagement().
                sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().authorizeRequests().
                antMatchers(swaggerPath).permitAll().
                antMatchers("/login").anonymous().
                antMatchers("/user/userInfo").authenticated().
                antMatchers("/logout").authenticated().
                antMatchers("/comment").authenticated().
                //Swigger集成Security的解决方法，将相关的路径全部放行
                anyRequest().permitAll();
        http.logout().disable();
        //http.formLogin().disable();
        http.exceptionHandling().authenticationEntryPoint(authHandler);
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors();
    }
}
