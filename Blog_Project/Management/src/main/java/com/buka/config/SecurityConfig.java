package com.buka.config;


import com.buka.filter.JwtAuthenticationTokenFilter;
import com.buka.security.AuthHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationTokenFilter authFilter;
    @Autowired
    private AuthHandler authHandler;
    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;
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
        http.csrf().disable().
                sessionManagement().
                sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().authorizeRequests().
                antMatchers("/user/login").anonymous().
//                antMatchers("/user/userInfo").authenticated().
//                antMatchers("/user/logout").authenticated().
//                antMatchers("/comment").authenticated().
                anyRequest().authenticated();
        http.logout().disable();
        //http.formLogin().disable();
        http.exceptionHandling().authenticationEntryPoint(authHandler);
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors();
    }
}
