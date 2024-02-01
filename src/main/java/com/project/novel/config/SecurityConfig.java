package com.project.novel.config;

import com.project.novel.constant.Grade;
import com.project.novel.service.CustomUserDetailsService;
import com.project.novel.service.OAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import java.util.UUID;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final OAuth2DetailsService oAuth2DetailsService;
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/img/**", "/auth/**","/css/**","/js/**","/images/**")
                        .permitAll()
                        .requestMatchers("/admin/**").hasRole(Grade.ROLE_ADMIN.getRole()) // ADMIN ROLE 만 접근 가능
                        .anyRequest()
                        .authenticated())
                .formLogin((form) -> form
                        .loginPage("/auth/login")
                        .usernameParameter("userId")
                        .passwordParameter("password")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/",true)
                        .permitAll())
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                        .logoutSuccessUrl("/auth/login")
                        .deleteCookies("JSESSIONID") // 로그아웃 할 때. JSEESIONID 제거
                        .invalidateHttpSession(true) // 로그아웃 할 때, 세션 종료
                        .clearAuthentication(true)) // 로그아웃 할 때, 권한 제거
                .oauth2Login((oauth2Login) -> oauth2Login
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/")
                        .userInfoEndpoint((userInfo) -> userInfo.userService(oAuth2DetailsService)))
                .rememberMe((auth -> auth
                        .rememberMeParameter("rememberUser")
                        .key(UUID.randomUUID().toString())
                        .userDetailsService(customUserDetailsService)
                        .tokenValiditySeconds(60*60*24)))
                .csrf((csrf) -> csrf.disable());
        return httpSecurity.build();
    }
}

