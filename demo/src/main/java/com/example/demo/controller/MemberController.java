package com.example.demo.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
@RequestMapping("/member")

public class MemberController {
    // 회원가입 : /member/register
    // 로그인 : /member/logon
    // 로그아웃 : /member/logout
    // 비밀번호 변경 : /member/change

    // http://localhost:8080/member/register

    // void : templats/member/register.html
    @GetMapping("/register")
    public void getRegister() {
        log.info("회원가입");
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute("mDTO") MemberDTO memberDTO, RedirectAttributes rttr) {
        log.info("회원가입 요청 {}", memberDTO);

        // 로그인 페이지로 이동
        // redirect(=다시요청)방식으로 가면서 값을 보내고 싶다면?(=기존값)
        rttr.addAttribute("userid", memberDTO.getUserid()); // 주소창에 파라미터로 값이 넘어감.
        rttr.addFlashAttribute("password", memberDTO.getPassword());
        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public void getLogin() {
        log.info("로그인");
    }

    @PostMapping("/login")
    // public void postLogin(String userid, String password) {
    // public void postLogin(LoginDTO loginDTO) {
    public void postLogin(HttpServletRequest request) {

        // getparameter는 form 안 선언된 이름과 같게 씀.
        // HttpServletRequest : 사용자, 정보, 서버 쪽 정보 추출
        // spring framework 없었다면, 아래와 같이 선언
        String userid = request.getParameter("userid");
        String password = request.getParameter("password");
        String remote = request.getRemoteAddr();
        String local = request.getLocalAddr();

        log.info("로그인요청 {}, {}", userid, password);
        log.info("클라이언트 정보 {}, {}", remote, local);
        // void는 무조건 template 찾기
    }

    @GetMapping("/logout")
    public void getLogout() {
        log.info("로그아웃");
    }

    @GetMapping("/change")
    public void getChange() {
        log.info("비밀번호변경");
    }

}
