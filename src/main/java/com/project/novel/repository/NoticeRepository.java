package com.project.novel.repository;

import com.project.novel.entity.NoticeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {


    // 제목으로 검색
    Page<NoticeEntity> findBySubjectContaining(String title, Pageable pageable);

    // 내용으로 검색
    Page<NoticeEntity> findByContentContaining(String content, Pageable pageable);

    // 작성자 아이디로 검색
    Page<NoticeEntity> findByMemberIdContaining(String writerId, Pageable pageable);

}
