package com.project.novel.config;

import com.project.novel.service.CustomUserDetailsService;
import com.project.novel.service.OAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final OAuth2DetailsService oAuth2DetailsService;
    //로그인 실패 핸들러 의존성 주입
    private final AuthenticationFailureHandler customFailureHandler;
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/auth/**","/css/**","/js/**","/images/**", "/img/**")
                        .permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN") // ADMIN 만 접근 가능
                        .requestMatchers("/notice/modify/**","/notice/delete/**").hasRole("ADMIN") // ADMIN 만 접근 가능
                        .anyRequest()
                        .authenticated())
                .formLogin((form) -> form
                        .loginPage("/auth/login")
                        .usernameParameter("userId")
                        .passwordParameter("password")
                        .loginProcessingUrl("/auth/login")
                        .failureHandler(customFailureHandler) // 로그인 실패 핸들러
                        .defaultSuccessUrl("/",true)
                        .permitAll())
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                        .logoutSuccessUrl("/auth/login")
                        .invalidateHttpSession(true) // 로그아웃 할 때, 세션 종료
                        .deleteCookies("JSESSIONID") // 로그아웃 할 때. JSEESIONID 제거
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
    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}
