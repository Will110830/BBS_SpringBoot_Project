package com.kh.demo.web.form.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginMember {

  private Long id;
  private String name;
  private String email;

}
