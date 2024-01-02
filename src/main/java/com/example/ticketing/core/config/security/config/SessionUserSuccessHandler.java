package com.example.ticketing.core.config.security.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

public class SessionUserSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // 로그인 성공시 이동할 페이지
        setDefaultTargetUrl("/");
        // 로그인 성공시 이동할 페이지 이동
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
