package com.example.ticketing.web.controller;

import com.example.ticketing.core.config.security.auth.SessionUser;
import com.example.ticketing.service.user.UserService;
import com.example.ticketing.web.dto.user.JoinUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")  // get post 등 여러 매핑을 포함해 다 이동 용도
@RequiredArgsConstructor // final 생성자 자동 생성
public class MainController {

    final private UserService userService;

    @GetMapping
    public String mainpage (Model model) {
        SessionUser sessionUser = SessionUser.getLoginUser(); // 로그인 유저 정보
//        JoinUserDto joinUserDto = userService.loginUserInfo();

        model.addAttribute("userInfo", sessionUser);
        return "main/main";
    }

    @GetMapping("login")
    public String login () {

        return "login";
    }

}
