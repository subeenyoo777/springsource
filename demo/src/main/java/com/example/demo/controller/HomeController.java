package com.example.demo.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Log4j2
@Controller

public class HomeController {

    // http://locallhost:8080/ 으로 요청 들어오면? ▶ home
    @GetMapping("/")
    public String getHome() {
        log.info("home 요청");
        return "home";
    }

    // http://locallhost:8080/ 으로 요청
    @GetMapping("/basic")
    public String getMethodName() {
        return "Info";
    }

    @PostMapping("/basic")
    // Model : springframework 타입으로 선언
    public String postAdd(@ModelAttribute("num1") int num1, @ModelAttribute("num2") int num2, Model model) {
        log.info("덧셈요청 {},{}", num1, num2);
        // 덧셈결과를 info 로 전송
        int result = num1 + num2;
        // name : 화면단에서 불러서 사용.
        // model.addAttribute("name", value) 형식으로 사용.
        model.addAttribute("result", result);
        // model.addAttribute("num1", num1);
        // model.addAttribute("num2", num2);

        return "info";
    }
}
