package com.example.ticketing.web.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")  // get post 등 여러 매핑을 포함해 다 이동 용도
@RequiredArgsConstructor // final 생성자 자동 생성
public class manageController {

    @GetMapping
    public String airView() {

        return "/admin/air";
    }

    @GetMapping("/course")
    public String courseView() {

        return "/admin/course";
    }

    @GetMapping("/course/add")
    public String courseAddView() {

        return "/admin/course/add";
    }
}
