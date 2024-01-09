package com.example.ticketing.service.user;

import com.example.ticketing.domain.user.User;
import com.example.ticketing.repository.user.UserRepository;
import com.example.ticketing.web.dto.user.JoinUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public String idCheck(String mberId) {
        Optional<User> user = userRepository.findById(mberId);

        if (user.isPresent()) {
            return "Y";
        }
        return "N";
    }

    public JoinUserDto loginUserInfo() {
        JoinUserDto joinUserDto = new JoinUserDto(); // 정보를 담을 그릇

//        joinUserDto
        return joinUserDto;
    }

    @Transactional
    public User saveMember(JoinUserDto joinUserDto) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        joinUserDto.setPw(passwordEncoder.encode(joinUserDto.getPw()));
        User user = User.save(joinUserDto);
        return userRepository.save(user);
    }
}
