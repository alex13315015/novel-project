package com.project.novel.config;

import com.project.novel.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/","/auth/**","/css/**","/js/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                    .formLogin((form) -> form
                            .loginPage("/auth/login")
                            .usernameParameter("userId")
                            .passwordParameter("password")
                            .loginProcessingUrl("/auth/login")
                            .defaultSuccessUrl("/",true)
                            .permitAll()
                    )
                    .logout((logout) -> logout
                            .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                            .logoutSuccessUrl("/auth/login")
                            .invalidateHttpSession(true)
                    )
                .rememberMe((auth -> auth
                        .rememberMeParameter("rememberUser")
                        .key(UUID.randomUUID().toString())
                        .userDetailsService(customUserDetailsService)
                        .tokenValiditySeconds(60*60*24)
                ))
                .csrf((csrf) -> csrf.disable());
        return httpSecurity.build();

    }
}
