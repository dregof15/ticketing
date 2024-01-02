package com.example.ticketing.core.config.security.auth;

import com.example.ticketing.domain.user.User;
import com.example.ticketing.repository.user.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SessionUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        User user = userRepository.findById(userId).orElseThrow(EntityExistsException::new);

        if (user == null) {
            throw new UsernameNotFoundException("[" + userId + "] 해당 아이디가 존재하지 않습니다.");
        }

        if (!"Y".equals(user.getUsedYn())) {
            throw new UsernameNotFoundException("[" + userId + "] 는 현재 사용할 수 없는 계정입니다.");
        }

        if (!"N".equals(user.getDelYn())) {
            throw new UsernameNotFoundException("[" + userId + "] 는 현재 삭제 된 계정입니다.");
        }

        return new SessionUser(user);
    }
}
