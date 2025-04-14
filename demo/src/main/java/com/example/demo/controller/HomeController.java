package com.example.demo.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

}
