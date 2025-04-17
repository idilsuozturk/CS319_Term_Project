package com.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


        private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http   .csrf(csrf -> csrf.disable()) // test amaçlı, prod'da dikkat!
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/frontend/**", "/login", "/register", "adminpanel/dist/pages/login.html").permitAll() // html ve login sayfaları herkese açık
                                .requestMatchers("/adminpanel/dist/pages/**").authenticated()//.hasRole("ADMIN")
                                .requestMatchers(
                                    "/adminpanel/assets/**",  
                                    "/adminpanel/dist/assets/**").permitAll()
                                .requestMatchers("/api/**").authenticated() // tüm API çağrıları login gerektirir
                                .anyRequest().permitAll() // diğer her şey açık
                )

                .formLogin(form -> form
                                .loginPage("/adminpanel/dist/pages/login.html") // özel login sayfan
                                .loginProcessingUrl("/login") // form'un action'ı buraya olmalı
                                .defaultSuccessUrl("/adminpanel/dist/pages/index.html", true)
                                .failureUrl("/adminpanel/dist/pages/login.html?error=true")
                                .permitAll()
                )
                .logout(logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/frontend/login.html")
                                .permitAll()
                );

        //.csrf(csrf -> csrf.disable())
                        /*.antMatchers("/login", "/process_register").permitAll()
                        .anyRequest().authenticated()
                        .and()
                        .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/
                        /*.authorizeHttpRequests((authorize) -> authorize
				.anyRequest().authenticated()
			)
            .httpBasic(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults());*/

        

        return http.build();
}

  
    @Bean
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
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}