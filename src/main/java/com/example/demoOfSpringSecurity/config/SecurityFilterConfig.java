package com.example.demoOfSpringSecurity.config;

import com.example.demoOfSpringSecurity.customJwt.JwtService;
import com.example.demoOfSpringSecurity.filter.JwtFilter;
import com.example.demoOfSpringSecurity.handler.OAuth2LoginSuccessHandler;
import com.example.demoOfSpringSecurity.service.CustomUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityFilterConfig {


    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

// day 1
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



//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
//        httpSecurity.csrf(csrf->csrf.disable() );
//        httpSecurity.authorizeHttpRequests(request-> request.requestMatchers("/k/create").permitAll().requestMatchers("/k/get").hasRole("ADMIN").requestMatchers("/k/getAlluser").hasRole("ADMIN").anyRequest().authenticated());
//        httpSecurity.formLogin(Customizer.withDefaults());
//        httpSecurity.httpBasic(Customizer.withDefaults());
//        return  httpSecurity.build();
//    }






// 25 aug login
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
//        httpSecurity.csrf(csrf->csrf.disable() );
//        httpSecurity.authorizeHttpRequests(request->
//                request.requestMatchers("/k/create").permitAll().
//                        requestMatchers("/k/login").permitAll()
//                        .requestMatchers("/k/profile").authenticated()
//                        .requestMatchers("/k/get/**").hasRole("ADMIN")
//                        .requestMatchers("/k/getAlluser").hasRole("ADMIN").anyRequest().authenticated());
//        httpSecurity.formLogin(Customizer.withDefaults());
//        httpSecurity.httpBasic(Customizer.withDefaults());
//        return  httpSecurity.build();
//    }







// 26 aug jwt
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtService jwtService
            , CustomUserService customUserService,
                                                   AuthenticationEntryPoint authenticationEntryPoint

    , OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler
    ){

     JwtFilter jwtFilter =new JwtFilter(jwtService, customUserService);




        httpSecurity.csrf(csrf->csrf.disable() );

        httpSecurity.authorizeHttpRequests(request->
                request.requestMatchers("/k/create","/oauth2/**","/login/**").permitAll().
                        requestMatchers("/k/login").permitAll()
                        .requestMatchers("/k/profile").authenticated()
                        .requestMatchers("/k/get/**").hasRole("ADMIN")
                        .requestMatchers("/k/getAlluser").hasRole("ADMIN").anyRequest().authenticated());
        httpSecurity.sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex-> ex.authenticationEntryPoint(authenticationEntryPoint))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2->oauth2.successHandler(oAuth2LoginSuccessHandler));
        return  httpSecurity.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(CustomUserService customUserService , PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider(customUserService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return  new ProviderManager(daoAuthenticationProvider);
    }


    @Bean
    public  AuthenticationEntryPoint jwtAuthenticationEntryPoint(){
        return  (request, response, authException) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("{\"Status\" :401 , \"error\":\"Unauthorized \", "+ " \"message \" : \"missing or invalid bearer token \" }");
        };
    }




}
