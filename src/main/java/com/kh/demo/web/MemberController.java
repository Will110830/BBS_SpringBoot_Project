package com.kh.demo.web;

import com.kh.demo.domain.member.MemberService;
import com.kh.demo.web.form.member.JoinForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class MemberController {

  private final MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  // 회원가입 폼 출력
  @GetMapping("/member/join")
  public String joinForm(Model model) {
    model.addAttribute("joinForm", new JoinForm());
    return "member/joinForm";
  }

  // 회원가입 처리
  @PostMapping("/member/join")
  public String join(@Valid @ModelAttribute("joinForm") JoinForm joinForm,
                     BindingResult bindingResult,
                     Model model) {
    // 기본 유효성 검사 실패시 다시 joinForm.html로
    if (bindingResult.hasErrors()) {
      return "member/joinForm";
    }

    // 비밀번호와 비밀번호 확인이 일치하지 않을 경우 에러 추가
    if (!joinForm.getPw().equals(joinForm.getPwCheck())) {
      bindingResult.rejectValue("pwCheck", "pwCheck.invalid", "비밀번호가 일치하지 않습니다.");
      return "member/joinForm";
    }

    try {
      memberService.join(joinForm);
    } catch (IllegalStateException e) {
      model.addAttribute("errorMessage", e.getMessage());
      return "member/joinForm";
    }

    return "redirect:/";
  }
}
