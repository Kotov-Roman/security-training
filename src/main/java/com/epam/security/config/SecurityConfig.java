package com.epam.security.config;

import com.epam.security.domain.Permissions;
import com.epam.security.handler.AuthFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthFailureHandler authFailureHandler) throws Exception {
        return http
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers("/about", "/login*", "/users/*").permitAll()
                        .antMatchers("/info").hasAuthority(Permissions.VIEW_INFO.getAuthority())
                        .antMatchers("/admin").hasAuthority(Permissions.VIEW_ADMIN.getAuthority())
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .failureHandler(authFailureHandler)
                        .permitAll()
                )
                .logout(form -> form
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/logout-success")
                        .permitAll()
                )
                .build();
    }

    @Bean
    public AuthenticationProvider authProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);

        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
