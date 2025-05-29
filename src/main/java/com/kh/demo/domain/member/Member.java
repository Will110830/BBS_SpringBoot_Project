package com.kh.demo.domain.member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_gen")
  @SequenceGenerator(name = "member_seq_gen", sequenceName = "member_seq", allocationSize = 1)
  private Long id;

  private String name;
  private String email;
  private String password;


  public Member(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
  }

    // 클래스 위에 이미 붙였으면 생략 가능
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Member{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        '}';
  }
}
