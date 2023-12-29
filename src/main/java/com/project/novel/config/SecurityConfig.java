package com.project.novel.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
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
                .csrf((csrf) -> csrf.disable());

        return httpSecurity.build();

    }
}
