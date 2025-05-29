package com.kh.demo.web;

import com.kh.demo.web.form.login.LoginForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {

  @GetMapping("/login")
  public String loginForm(@RequestParam(value = "error", required = false) String error,
                          Model model) {
    model.addAttribute("loginForm", new LoginForm());
    if (error != null) {
      model.addAttribute("errorMessage", "아이디 또는 비밀번호가 올바르지 않습니다.");
    }
    return "login/loginForm";
  }


}
