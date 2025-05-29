package com.kh.demo.web;

import com.kh.demo.config.auth.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/")
  public String index(@AuthenticationPrincipal CustomUserDetails loginMember) {
    if (loginMember != null && loginMember.getUsername() != null) {
      return "redirect:/main";
    }
    return "index";
  }
}
