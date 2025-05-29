package com.kh.demo.domain.login;

import com.kh.demo.domain.member.Member;
import com.kh.demo.domain.member.MemberRepository;
import com.kh.demo.web.form.login.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

  private final MemberRepository memberRepository;

  public Member login(LoginForm loginForm) {
    Optional<Member> memberOpt = memberRepository.findByEmail(loginForm.getEmail());

    if (memberOpt.isPresent()) {
      Member member = memberOpt.get();
      if (member.getPassword().equals(loginForm.getPassword())) {
        return member;  // 로그인 성공 시 Member 객체 반환
      }
    }
    return null;  // 로그인 실패 시 null 반환
  }
}
