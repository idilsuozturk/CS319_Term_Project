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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;

import com.services.AdminDetailsService;
import com.services.CustomUserDetailsService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final CustomUserDetailsService userDetailsService;
    private final AdminDetailsService adminDetailsService;
    private CustomSuccessHandler customSuccessHandler;
    public SecurityConfig(CustomUserDetailsService userDetailsService, AdminDetailsService adminDetailsService, CustomSuccessHandler customSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.adminDetailsService = adminDetailsService;
        this.customSuccessHandler = customSuccessHandler;
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
        .securityMatcher("/adminpanel/**") // Only match admin panel paths
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/adminpanel/login", "/adminpanel/register", "/adminpanel/dist/pages/login.html").permitAll()
            .requestMatchers("/adminpanel/dist/pages/**").hasRole("ADMIN")
            .requestMatchers("/adminpanel/dist/pages/403.html").permitAll()
            .requestMatchers("/adminpanel/assets/**", "/adminpanel/dist/**").permitAll()
            .anyRequest().permitAll()//.authenticated() // All other requests require authentication
        )

                     .formLogin(form -> form
                .loginPage("/adminpanel/dist/pages/login.html") // özel login sayfan
                .loginProcessingUrl("/adminpanel/login") // form'un action'ı buraya olmalı
                .defaultSuccessUrl("/adminpanel/dist/pages/index.html", true)
                .failureUrl("/adminpanel/dist/pages/login.html?error=true")
                .permitAll()
            ).userDetailsService(adminDetailsService)

            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler())
            )
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
        .securityMatcher("/**")

        .csrf(csrf -> csrf.disable())
        
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/frontend/login.html", "/frontend/assets/**", "/frontend/login", "/frontend/register").permitAll()
            .requestMatchers("/frontend/**").permitAll()
            .requestMatchers("/api/user-info").authenticated()
            .requestMatchers("/api/**").authenticated()
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/frontend/login.html")
            .loginProcessingUrl("/frontend/login")
            .successHandler(customSuccessHandler)
            .failureUrl("/frontend/login.html?error=true")
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/frontend/login.html")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .permitAll()
        )
        .exceptionHandling(ex -> ex
            .authenticationEntryPoint((request, response, authException) -> {
                if (request.getRequestURI().startsWith("/api/")) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Unauthorized\"}");
                } else {
                    response.sendRedirect("/frontend/login.html");
                }
            })
        )
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        )
        .userDetailsService(userDetailsService);
    
    return http.build();
}

/*
    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http   
            .securityMatcher("/adminpanel/**", "/api/**") // sadece admin paneli için geçerli
            .csrf(csrf -> csrf.disable()) // test amaçlı, prod'da dikkat!
            .authorizeHttpRequests(auth -> auth
                .requestMatchers( "/adminpanel/login", "/adminpanel/register", "/adminpanel/dist/pages/login.html").permitAll() // html ve login sayfaları herkese açık
                .requestMatchers("/adminpanel/dist/pages/**").permitAll()//.hasRole("ADMIN") // admin paneli sayfaları sadece admin'e açık
                .requestMatchers("/adminpanel/dist/pages/403.html").permitAll()
                .requestMatchers(
                    "/adminpanel/assets/**",  
                    "/adminpanel/dist/**").permitAll()
                    

                .requestMatchers("/api/**").hasRole("ADMIN")//.authenticated() // tüm API çağrıları login gerektirir
                .anyRequest().denyAll() // diğer her şey açık
            )

             .formLogin(form -> form
                .loginPage("/adminpanel/dist/pages/login.html") // özel login sayfan
                .loginProcessingUrl("/adminpanel/login") // form'un action'ı buraya olmalı
                .defaultSuccessUrl("/adminpanel/dist/pages/index.html", true)
                .failureUrl("/adminpanel/dist/pages/login.html?error=true")
                .permitAll()
            ).userDetailsService(adminDetailsService)

            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler())
            )
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
        .securityMatcher("/**") 
        .csrf(csrf -> csrf.disable()) 
        .authorizeHttpRequests(auth -> auth
            // Frontend paths
            .requestMatchers("/frontend/login.html", "/frontend/assets/**", "/frontend/login", "/frontend/register").permitAll()
            .requestMatchers("/frontend/**").permitAll()
            // Admin panel paths
            .requestMatchers("/adminpanel/login.html", "/adminpanel/assets/**", "/adminpanel/login").permitAll()
            .requestMatchers("/adminpanel/**").permitAll()
            // API paths
            .requestMatchers("/api/user-info").authenticated()
            .requestMatchers("/api/**").authenticated()
            .anyRequest().permitAll()
        )
        /*
public SecurityFilterChain userSecurity(HttpSecurity http) throws Exception {
    http
        .securityMatcher("/frontend/**") 
        .csrf(csrf -> csrf.disable()) 
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/frontend/login.html", "/frontend/assets/**", "/frontend/login", "/frontend/register").permitAll()
             .requestMatchers("/api/user-info").authenticated() 
            //.requestMatchers("/api/**").permitAll()
            .requestMatchers( "/api/**").authenticated() // tüm API çağrıları login gerektirir
            .requestMatchers( "/frontend/**").permitAll()//.authenticated()
           
            .anyRequest().permitAll()//.denyAll()//.hasRole("USER")
        )
       /* .formLogin(form -> form
            .loginPage("/frontend/login.html")
            .loginProcessingUrl("/frontend/login")
            /*.successHandler((request, response, authentication) -> {
                //SecurityContextHolder.getContext().setAuthentication(authentication);
                
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

                  HttpSession session = request.getSession();
                    session.setAttribute("userId", user.getId());
                    session.setAttribute("username", user.getUsername());
                    session.setAttribute("role", user.getAuthorities().toString());

                response.sendRedirect("/frontend/index.html");
            })*//*.successHandler((request, response, authentication) -> {
                    CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
                    request.getSession().setAttribute("userId", user.getId());
                    request.getSession().setAttribute("username", user.getUsername());
                    request.getSession().setAttribute("role", user.getAuthorities().toString());
                    System.out.println("Saved userId in session: " + user.getId());  // log for debug
                    response.sendRedirect("/frontend/index.html");
                })

            .failureUrl("/frontend/login.html?error=true")
            .permitAll()
        )

        .exceptionHandling(ex -> ex
            .authenticationEntryPoint(authenticationEntryPoint())
            .accessDeniedHandler(accessDeniedHandler())
        )
        .logout(logout -> logout
            .logoutUrl("/frontend/logout")
            .logoutSuccessUrl("/frontend/login.html")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
        ) .userDetailsService(userDetailsService);*/

          /* .formLogin(form -> form
            .loginPage("/frontend/login.html")
            .loginProcessingUrl("/frontend/login")
            .defaultSuccessUrl("/frontend/index.html", true)
            .failureUrl("/frontend/login.html?error=true")
            .permitAll()
        )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/frontend/login.html")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )

            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )
""
            .userDetailsService(userDetailsService);
    return http.build();
}*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/api/**")
                    .allowedOrigins("http://localhost:8081") // or your frontend URL
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowCredentials(true);
        }
    };
}
}