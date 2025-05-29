package com.kh.demo.web;

import com.kh.demo.domain.entity.Bbs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BbsController {

  @Autowired
  private BbsService bbsService;

  @GetMapping("/bbs/list")
  public String showBoardList(
      @RequestParam(name = "page", defaultValue = "0") int page,
      Model model) {

    int pageSize = 10;

    Page<Bbs> bbsPage = bbsService.getPosts(PageRequest.of(page, pageSize));
    Bbs notice = bbsService.getNotice();

    model.addAttribute("bbsPage", bbsPage);
    model.addAttribute("notice", notice);

    return "bbs/list";
  }
}
