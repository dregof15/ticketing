package com.example.ticketing.core.config.security.provider;

import com.example.ticketing.core.config.security.auth.SessionUser;
import com.example.ticketing.core.config.security.auth.SessionUserService;
import com.example.ticketing.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SessionUserProvider implements AuthenticationProvider {

    @Autowired
    private SessionUserService sessionUserService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String userId = authentication.getName(); // 사용자 입력한 ID
        String password = (String)authentication.getCredentials(); // 사용자가 입력한 PW

        // 생성해둔 SessionUserService 에서 loadUserByUsername 메소드를 호출하여 사용자 정보를 가져옴.
        SessionUser sessionUser = (SessionUser) sessionUserService.loadUserByUsername(userId); // DB의 user 정보

        /*======================================= 비밀번호 비교 ==========================================*/
        String dbPassword = sessionUser.getPassword(); // DB 페스워드
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(password, dbPassword)) {
            throw new BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
        /*===============================================================================================*/

        // id pw 일치 시 throw 를 넘어서 밑에 코드가 실행됨.
        User user = sessionUser.getUser();
        if (user == null || "N".equals(user.getUsedYn())) {
            throw new BadCredentialsException("현재 아이디는 사용할 수 없는 계정입니다.");
        }

        // 인증이 완료된 객체 반환  이는 SecurityContextHolder.getContext()에 저장됨.
        return new UsernamePasswordAuthenticationToken(sessionUser,null,sessionUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
