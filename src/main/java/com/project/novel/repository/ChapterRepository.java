package com.project.novel.repository;

import com.project.novel.dto.ChapterDto;
import com.project.novel.entity.Chapter;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    @Query("SELECT new com.project.novel.dto.ChapterDto(c.id, c.title, c.createdAt, c.hits) " +
            "FROM Chapter c WHERE c.book.id = :bookId AND c.isActive = true")
    Page<ChapterDto> findAllByBookId(@Param("bookId") Long bookId, Pageable pageable);

    @Query("SELECT SUM(c.hits) FROM Chapter c WHERE c.book.id = :bookId AND c.isActive = true")
    Long sumHitsByBookId(@Param("bookId") Long bookId);

    @Modifying
    @Transactional
    @Query("update Chapter c set c.hits = c.hits + :hits where c.id = :chapterId")
    void updateHits(@Param("chapterId") Long chapterId, @Param("hits") Long hits);

    @Query("SELECT c FROM Chapter c WHERE c.isActive = false")
    List<Chapter> findByIsActive();

    @Query("SELECT c FROM Chapter c WHERE c.book.id = :bookId AND c.isActive = true " +
            "ORDER BY c.id ASC")
    List<Chapter> findAllByBookId(@Param("bookId") Long bookId);
}
