package com.example.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

  @GetMapping("/logout")
  public String logout() {
    // 세션 정보 초기화
    SecurityContextHolder.clearContext();

    // index 페이지로 리다이렉트
    return "redirect:/index";
  }
}
