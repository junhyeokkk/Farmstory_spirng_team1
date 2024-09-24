package com.farmstory.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {


    //어플리케이션 실행시 등록됨
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 로그인 설정
        http.formLogin(login -> login
                .loginPage("/category/user/login")
                .defaultSuccessUrl("/")
                .failureUrl("/category/user/login?success=100")
                .usernameParameter("uid")
                .passwordParameter("pass"));

        // 로그아웃 설정
        http.logout(logout -> logout
                .invalidateHttpSession(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/category/user/login?success=101"));
        // 인가 설정
        http.authorizeHttpRequests(authorize -> authorize
                                                    .requestMatchers("/").permitAll()
                                                    .requestMatchers("/article/**").authenticated()
                                                    .requestMatchers("/user/**").permitAll()
                                                    .anyRequest().permitAll());

        // 기타 보안 설정
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();



//           .authorizeHttpRequests((requests) -> requests
//                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()  // 정적 리소스 허용
//                .requestMatchers("/", "/index", "/**").permitAll()  // 인증 없이 접근 허용할 경로
//                .anyRequest().permitAll() // 그 외의 요청은 인증 필요
//        )

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        //평문을 암호화시킬때 암호문 만들때 도와주는 encoder
        return new BCryptPasswordEncoder();
    }
}
