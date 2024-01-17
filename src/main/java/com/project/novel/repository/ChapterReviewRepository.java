package com.project.novel.repository;

import com.project.novel.entity.ChapterReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChapterReviewRepository extends JpaRepository<ChapterReview, Long> {
}
