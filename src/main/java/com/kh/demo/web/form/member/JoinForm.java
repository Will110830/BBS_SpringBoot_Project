package com.kh.demo.web.form.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JoinForm {

  @NotBlank(message = "이름은 필수 입력 항목입니다.")
  private String name;

  @NotBlank(message = "이메일은 필수 입력 항목입니다.")
  @Email(message = "유효한 이메일 형식이 아닙니다.")
  private String email;

  @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
  @Size(min = 6, max = 20, message = "비밀번호는 6자 이상 20자 이하로 입력해주세요.")
  private String pw;

  @NotBlank(message = "비밀번호 확인은 필수 입력 항목입니다.")
  private String pwCheck;

}