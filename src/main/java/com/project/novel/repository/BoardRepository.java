package com.project.novel.repository;

import com.project.novel.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    @Modifying
    @Query(value = "update BoardEntity b set b.hit=b.hit+1 where b.id=:id")
    void updateHit(@Param("id") Long id);


    @Query("SELECT b FROM BoardEntity b ORDER BY b.id DESC")
    List<BoardEntity> findAllDesc();


    // 제목으로 검색
    Page<BoardEntity> findBySubjectContaining(String title, Pageable pageable);

    // 내용으로 검색
    Page<BoardEntity> findByContentContaining(String content, Pageable pageable);

    // 작성자 아이디로 검색
    Page<BoardEntity> findByMemberIdContaining(String writerId, Pageable pageable);


}
