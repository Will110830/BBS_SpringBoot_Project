package com.kh.demo.web;

import com.kh.demo.config.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

  private final BbsService bbsService;

  @GetMapping("/main")
  public String mainPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
    if (userDetails == null) {
      return "index";
    }

    model.addAttribute("loginMember", userDetails.getMember());
    model.addAttribute("noticePosts", bbsService.findRecentPosts("notice", 3));
    model.addAttribute("reviewPosts", bbsService.findRecentPosts("free", 3));
    model.addAttribute("suggestionPosts", bbsService.findRecentPosts("qna", 3));

    return "main/main";
  }
}
