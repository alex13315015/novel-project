package com.project.novel.repository.book;

import com.project.novel.entity.chapter.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {

}
