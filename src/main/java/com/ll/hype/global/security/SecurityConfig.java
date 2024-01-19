package com.ll.hype.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http
                .authorizeHttpRequests(authorizeRequests ->
                                authorizeRequests

                                        .requestMatchers("/members/**")
                                        .hasRole("MEMBER") // ROLE_MEMBER

                                        .requestMatchers("/admin/**")
                                        .hasRole("ADMIN") // ROLE_ADMIN

                                        .requestMatchers("/paid/**")
                                        .hasRole("PAID")
                                        .requestMatchers("/**")// ROLE_PAID
                                        .permitAll()
                )
                .headers(
                        headers ->
                                headers.frameOptions(
                                        HeadersConfigurer.FrameOptionsConfig::disable
                                )
                )
                // Spring Security에서 form 기반 로그인을 하기 위한 설정
                // form 기반 로그인 설정 : 사용자 정의 로그인 페이지 및 로그인 성공 후의 기본
                .formLogin(
                        formLogin -> { // form 기반 로그인 필터를 위한 빌더
                            formLogin
                                    // Spring Security에서 제공하는 기본 로그인 페이지가 아닌, 직접 만든 페이지를 로그인 페이지로 사용하기 위한 설정
                                    .loginPage("/member/login") // get, post
                                    // 실제로 로그인 로직(스프링 시큐리티가 만들어놓은)을 처리하기 위한 Url
                                    .loginProcessingUrl("/member/login")
                                    // 로그인이 성공하면 요청을 돌려보낼 url
                                    .defaultSuccessUrl("/question/list");
                            // 로그인이 성공하면 요청을 돌려보낼 url
                            //.successForwardUrl("/question/list");  이부분 바꾸니까 성공!
                        }
                )
                // Spring Security에서 form 기반 로그아웃을 하기 위한 설정
                .logout(
                        logout -> { // form 기반 로그아웃 필터를 위한 빌더
                            logout
                                    // 로그아웃을 수행하기 위한 url을 등록
                                    // request matcher ant / mvc
                                    .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                                    // 로그아웃 성공 후 이동될 url
                                    .logoutSuccessUrl("/question/list")
                                    // 로그아웃 성공시, Session 만료 여부
                                    .invalidateHttpSession(true);
                        }
                )
                .csrf(
                        csrf ->
                                csrf.ignoringRequestMatchers(
                                        "/h2-console/**"
                                )
                );

        return http.build();
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

