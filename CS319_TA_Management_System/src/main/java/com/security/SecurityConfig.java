package com.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.services.AdminDetailsService;
import com.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final CustomUserDetailsService userDetailsService;
    private final AdminDetailsService adminDetailsService;
    public SecurityConfig(CustomUserDetailsService userDetailsService, AdminDetailsService adminDetailsService) {
        this.userDetailsService = userDetailsService;
        this.adminDetailsService = adminDetailsService;
    }

@Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            String uri = request.getRequestURI();
            if (uri.startsWith("/adminpanel")) {
                response.sendRedirect("/adminpanel/dist/pages/login.html");
            } else {
                response.sendRedirect("/frontend/login.html");
            }
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            String uri = request.getRequestURI();
             if (uri.startsWith("/adminpanel")) {
                response.sendRedirect("/adminpanel/dist/pages/403.html");
            } else {
                response.sendRedirect("/frontend/403.html");
            }
        };
    }


    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http   
            .securityMatcher("/adminpanel/**") // sadece admin paneli için geçerli
            .csrf(csrf -> csrf.disable()) // test amaçlı, prod'da dikkat!
            .authorizeHttpRequests(auth -> auth
                .requestMatchers( "/adminpanel/login", "/adminpanel/register", "/adminpanel/dist/pages/login.html").permitAll() // html ve login sayfaları herkese açık
                .requestMatchers("/adminpanel/dist/pages/**").permitAll()//.authenticated()
                .requestMatchers(
                    "/adminpanel/assets/**",  
                    "/adminpanel/dist/assets/**").permitAll()
                    .requestMatchers("/adminpanel/dist/**").permitAll()

                .requestMatchers("/api/**").permitAll()//.authenticated() // tüm API çağrıları login gerektirir
                .anyRequest().denyAll() // diğer her şey açık
            )

            /* .formLogin(form -> form
                .loginPage("/adminpanel/dist/pages/login.html") // özel login sayfan
                .loginProcessingUrl("/adminpanel/login") // form'un action'ı buraya olmalı
                .defaultSuccessUrl("/adminpanel/dist/pages/index.html", true)
                .failureUrl("/adminpanel/dist/pages/login.html?error=true")
                .permitAll()
            ).userDetailsService(adminDetailsService)

            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler())
            )*/
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/frontend/login.html")
                .permitAll()
            );
            
        return http.build();
}

@Bean
@Order(2)
public SecurityFilterChain userSecurity(HttpSecurity http) throws Exception {
    http
        .securityMatcher("/frontend/**") 
        .csrf(csrf -> csrf.disable()) 
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/frontend/login.html", "/frontend/assets/**", "/frontend/login", "/frontend/register").permitAll()
            .requestMatchers("/api/**", "/frontend/**").authenticated()
            .anyRequest().denyAll()//.hasRole("USER")
        )
        .formLogin(form -> form
            .loginPage("/frontend/login.html")
            .loginProcessingUrl("/frontend/login")
            .defaultSuccessUrl("/frontend/index.html", true)
            .failureUrl("/frontend/login.html?error=true")
            .permitAll()
        ) .userDetailsService(userDetailsService)

        .exceptionHandling(ex -> ex
            .authenticationEntryPoint(authenticationEntryPoint())
            .accessDeniedHandler(accessDeniedHandler())
        )
        .logout(logout -> logout
            .logoutUrl("/frontend/logout")
            .logoutSuccessUrl("/frontend/login.html")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
        );

    return http.build();
}
  
    /*@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); 
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}