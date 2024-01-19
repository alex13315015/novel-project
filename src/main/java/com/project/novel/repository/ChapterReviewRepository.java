package com.project.novel.repository;

import com.project.novel.dto.ChapterReviewDto;
import com.project.novel.entity.ChapterReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ChapterReviewRepository extends JpaRepository<ChapterReview, Long> {

    @Query("SELECT new com.project.novel.dto.ChapterReviewDto(cr.id, cr.member.nickname, cr.comments, cr.createdAt) FROM ChapterReview cr WHERE cr.chapter.id = :chapterId")
    Page<ChapterReviewDto> findAllByChapterId(@Param("chapterId") Long chapterId, Pageable pageable);

}
