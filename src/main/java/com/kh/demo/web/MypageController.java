package com.kh.demo.web;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.kh.demo.domain.member.Member;
import com.kh.demo.domain.member.NameUpdateRequest;
import com.kh.demo.domain.member.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MypageController {

  private final MemberService memberService;

  // 1. 마이페이지 뷰
  @GetMapping("/mypage")
  public String showMypage(Model model, Principal principal) {
    if (principal == null) {
      return "redirect:/";
    }

    String email = principal.getName();
    Member member = memberService.findByEmail(email);

    if (member != null) {
      model.addAttribute("name", member.getName());
    }
    return "my/mypage";
  }

  // 2. 이름 변경 API
  @PostMapping("/mypage/updateName")
  @ResponseBody
  public Map<String, Object> updateName(@RequestBody NameUpdateRequest request, Principal principal) {
    Map<String, Object> result = new HashMap<>();
    try {
      String email = principal.getName();
      boolean updated = memberService.updateName(email, request.getName());

      if (updated) {
        result.put("success", true);
      } else {
        result.put("success", false);
        result.put("message", "이름 변경을 실패했습니다");
      }
    } catch (Exception e) {
      result.put("success", false);
      result.put("message", "서버에 오류가 발생 했습니다");
    }
    return result;
  }
}
