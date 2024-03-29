package com.example.ticketing.core.config.security.config;

import com.example.ticketing.core.config.security.auth.SessionUserService;
import com.example.ticketing.core.config.security.provider.SessionUserProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.ErrorResponse;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.io.PrintWriter;
import java.util.logging.Handler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    /**
     * 이 메서드는 정적 자원에 대해 보안을 적용하지 않도록 설정한다.
     * 정적 자원은 보통 HTML, CSS, JavaScript, 이미지 파일 등을 의미하며, 이들에 대해 보안을 적용하지 않음으로써 성능을 향상시킬 수 있다.
     */

    // 생성해둔 SessionUserProvider 를 주입받음.
    // 해당 클래스로 SessionUserService 내부 로직 수행
    // 인증 처리 진행
    @Autowired
    SessionUserProvider sessionUserProvider;

    // 로그인 기억 용도 SessionUserProvider 내부
    // SessionUserService 선언
    @Autowired
    SessionUserService sessionUserService;

    // Spring Security 6 이상 버전 부터는
    // AuthenticationManagerBuilder 를 직접 생성하여 AuthenticationManager 를 생성해야함.
    @Autowired
    public void configure (AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(sessionUserProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {

        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);
        return http.authorizeHttpRequests(authz -> authz
                        .requestMatchers(mvc.pattern("/"), mvc.pattern("/login"), mvc.pattern("/join/**"), mvc.pattern("/css/**"), mvc.pattern("/images/**"), mvc.pattern("/js/user/join/**"), mvc.pattern("/js/common.js"),mvc.pattern("/favicon.ico"),mvc.pattern("/error"),mvc.pattern("/admin"),mvc.pattern("/admin/**")).permitAll()
                        .anyRequest().authenticated()
                ).formLogin((formLogin) ->
                        formLogin.loginPage("/login")
                                .defaultSuccessUrl("/")
                ).logout((logout) ->
                        logout.logoutSuccessUrl("/login")
                                .invalidateHttpSession(true)
                )
                .csrf(AbstractHttpConfigurer::disable)// 3번
                .exceptionHandling((exceptionConfig) ->
                        exceptionConfig.authenticationEntryPoint(unauthorizedEntryPoint).accessDeniedHandler(accessDeniedHandler)
                ) // 401 403 관련 예외처리
                .build();
    }
    private final AuthenticationEntryPoint unauthorizedEntryPoint =
            (request, response, authException) -> {
                ErrorResponse fail = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Spring security unauthorized...");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                String json = new ObjectMapper().writeValueAsString(fail);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.flush();
            };

    private final AccessDeniedHandler accessDeniedHandler =
            (request, response, accessDeniedException) -> {
                ErrorResponse fail = new ErrorResponse(HttpStatus.FORBIDDEN, "Spring security forbidden...");
                response.setStatus(HttpStatus.FORBIDDEN.value());
                String json = new ObjectMapper().writeValueAsString(fail);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.flush();
            };

    @Getter
    @RequiredArgsConstructor
    public class ErrorResponse {

        private final HttpStatus status;
        private final String message;
    }
}