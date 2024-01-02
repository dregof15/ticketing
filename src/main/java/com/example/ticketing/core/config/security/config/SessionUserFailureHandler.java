package com.example.ticketing.core.config.security.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

public class SessionUserFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        // 실패 메세지를 담기 위한 세션
        HttpSession session = request.getSession();
        // 세션에 실패 메시지 담기
        session.setAttribute("로그인이 실패 했습니다.",exception.getMessage());
        // 실패시 이동할 페이지
        setDefaultFailureUrl("/login");
        // 실패시 이동할 페이지로 이동
        super.onAuthenticationFailure(request, response, exception);
    }
}
