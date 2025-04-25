package com.example.demo.controller;

import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

@Log4j2
@RequestMapping("/board")
@Controller

public class BoardController {

    @GetMapping("/create")
    public void getCreate() {
        // return "board/create";
    }

    @PostMapping("/create")
    // public String postCreate(@ModelAttribute("name") String name,
    // RedirectAttributes rttr) {
    public void postCreate(String name, HttpSession session) {
        log.info("name 값 가져오기 {}", name);
        session.setAttribute("name1", name);

        // 어느 페이지( 여기서 템플릿 의미)로 이동하든지 name을 유지하고 싶다면?
        // -> 커맨드 객체 또는 ModelAttributes(or @ModelAttribute)
        // return "board/list"; // 포워드 방식

        // redirct 방식 + 값 유지
        // rttr.addFlashAttribute("name", name);
        // return "redirect:/board/list";

    }

    @GetMapping("/list")
    public void getList() {
        // return "board/list";
    }

    @GetMapping("/read")
    public void getRead() {
        // return "board/read";
    }

    @GetMapping("/update")
    public void getUpdate() {
        // return "board/update";
    }
}
