package com.example.ticketing.core.config.security.auth;

import com.example.ticketing.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@AllArgsConstructor
public class SessionUser implements UserDetails {

    private final User user;
    
//    유저 아이디
    private String userId;
//    유저 페스워드
    private String password;
//    유저 이름
    private String userNm;
//    유저 권한
    private String role;

    public SessionUser(User user) {
        this.user = user;
    }

//    member 계정의 권한을 담아두기 위한 부분
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

//    member 계정의 비밀번호를 담아두기 위한 부분
    @Override
    public String getPassword() {
        return null;
    }

//    member 계정의 아이디를 담아두기 위한 부분
    @Override
    public String getUsername() {
        return null;/* 내보내는 값 수정 필요 */
    }

//    계정이 만료되지 않았는지를 담아두기 위한 부분 (true : 만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

//    계정이 잠겨있지 않았는지를 담아두기 위한 부분 (true : 잠기지않음)
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

//    계정의 비밀번호가 만료되지 않았는지를 담아두기 위한 부분 (true : 만료안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

//    계정이 활성화되어있는지를 담아두기 위한 부분(true: 활성화됨)
    @Override
    public boolean isEnabled() {
        return false;
    }
}
