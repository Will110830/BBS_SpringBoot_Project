package com.kh.demo.domain.repository;

import com.kh.demo.domain.entity.Bbs;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BbsRepository extends JpaRepository<Bbs, Long> {

  Bbs findTopByTitleContainingOrderByBbsIdDesc(String keyword);

  // Pageable 사용해서 개수 제한 가능
  @Query("SELECT b FROM Bbs b WHERE b.title LIKE %:keyword% ORDER BY b.bbsId DESC")
  List<Bbs> findByTitleContainingOrderByBbsIdDesc(@Param("keyword") String keyword, Pageable pageable);
}
