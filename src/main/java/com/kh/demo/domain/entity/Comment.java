package com.kh.demo.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Comment {
  @Id
  @GeneratedValue
  private Long id;

  private Long postId;

  private String content;

  private String author;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}

