package com.example.security.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
       

        // 로그인 성공 시 어디로 이동할 것인가?

        // 기본은 로그인 작업 이전 페이지으로 이동.
ClubAuthMemberDTO clubAuthMemberDTO =       (ClubAuthMemberDTO) authentication.getPrincipal();

log.info("CustomLoginSuccessHandler {}" , clubAuthMemberDTO);
    }
    
}
