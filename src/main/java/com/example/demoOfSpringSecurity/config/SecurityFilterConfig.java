package com.example.demoOfSpringSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.net.http.HttpRequest;

@Configuration
public class SecurityFilterConfig {


    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
//        httpSecurity.csrf(csrf->csrf.disable() );
//        httpSecurity.authorizeHttpRequests(request-> request.requestMatchers("/k/create").permitAll().anyRequest().authenticated());
//        httpSecurity.formLogin(Customizer.withDefaults());
//        httpSecurity.httpBasic(Customizer.withDefaults());
//        return  httpSecurity.build();
//    }



//        @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
//        httpSecurity.csrf(csrf->csrf.disable() );
//        httpSecurity.authorizeHttpRequests(request-> request.requestMatchers("/k/create").permitAll().requestMatchers("/k/get").hasRole("ADMIN").requestMatchers("/k/getAlluser").hasRole("ADMIN").anyRequest().authenticated());
//        httpSecurity.formLogin(Customizer.withDefaults());
//        httpSecurity.httpBasic(Customizer.withDefaults());
//        return  httpSecurity.build();
//    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity.csrf(csrf->csrf.disable() );
        httpSecurity.authorizeHttpRequests(request-> request.requestMatchers("/k/create").permitAll().requestMatchers("/k/get").hasRole("ADMIN").requestMatchers("/k/getAlluser").hasRole("ADMIN").anyRequest().authenticated());
        httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.httpBasic(Customizer.withDefaults());
        return  httpSecurity.build();
    }
}
