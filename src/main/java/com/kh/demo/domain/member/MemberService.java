package com.kh.demo.domain.member;

import com.kh.demo.domain.member.Member;
import com.kh.demo.domain.member.MemberRepository;
import com.kh.demo.web.form.member.JoinForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;  // 추가

  public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
    this.memberRepository = memberRepository;
    this.passwordEncoder = passwordEncoder;  // 초기화
  }

  public void join(JoinForm joinForm) {

    if (memberRepository.existsByEmail(joinForm.getEmail())) {
      throw new IllegalStateException("이미 사용중인 이메일입니다.");
    }

    if (!joinForm.getPw().equals(joinForm.getPwCheck())) {
      throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
    }

    String encodedPw = passwordEncoder.encode(joinForm.getPw());  // 암호화

    Member member = new Member(joinForm.getName(), joinForm.getEmail(), encodedPw);
    memberRepository.save(member);
  }

  public Member findByEmail(String email) {
    return memberRepository.findByEmail(email).orElse(null);
  }

  // 이름 변경 메서드
  public boolean updateName(String email, String newName) {
    Member member = memberRepository.findByEmail(email).orElse(null);
    if (member == null) {
      return false;
    }
    member.setName(newName);
    return true;
  }
}

