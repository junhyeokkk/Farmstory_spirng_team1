package com.farmstory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 정적 리소스에 대한 접근을 허용
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()  // 정적 리소스 허용
                        .requestMatchers("/", "/index", "/**").permitAll()  // 인증 없이 접근 허용할 경로
                        .anyRequest().permitAll() // 그 외의 요청은 인증 필요
                )
                .formLogin((form) -> form
                        .loginPage("/user/login")  // 로그인 페이지 경로
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll
                )
                .headers(headers -> headers
                        .contentSecurityPolicy(policy -> policy
                                .policyDirectives("script-src 'self' 'unsafe-eval'; style-src 'self' 'unsafe-inline'")
                        )
                );


        return http.build();
    }


}
