package com.example.security.controller;

import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/sample")
public class SampleController {

    @GetMapping("/guest")
    public void getGuest() {
        log.info("guest");
    }

    @GetMapping("/admin")
    public void getAdmin() {
        log.info("admin");
    }

    @GetMapping("/member")
    public void getMember() {
        log.info("member");
    }

}
