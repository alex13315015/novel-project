package com.project.novel.repository;

import com.project.novel.dto.ChapterDto;
import com.project.novel.entity.Chapter;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    @Query("SELECT new com.project.novel.dto.ChapterDto(c.id, c.title, c.createdAt, c.hits) " +
            "FROM Chapter c WHERE c.book.id = :bookId " +
            "ORDER BY CASE WHEN :order = 'Oldest' THEN c.createdAt END ASC, " +
            "CASE WHEN :order = 'Update' THEN c.createdAt END DESC")
    List<ChapterDto> findAllByBookId(@Param("bookId") Long bookId, @Param("order") String order);


    @Modifying
    @Transactional
    @Query("update Chapter c set c.hits = c.hits + :hits where c.id = :chapterId")
    void updateHits(@Param("chapterId") Long chapterId, @Param("hits") Long hits);
}
