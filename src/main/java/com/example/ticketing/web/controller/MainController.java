package com.example.ticketing.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")  // get post 등 여러 매핑을 포함해 다 이동 용도
@RequiredArgsConstructor // final 생성자 자동 생성
public class MainController {
    @GetMapping
    public String mainpage () {

        return "main/main";
    }

    @GetMapping("login")
    public String login () {

        return "login";
    }

}
