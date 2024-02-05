package com.project.novel.repository;

import com.project.novel.dto.BookListDto;
import com.project.novel.entity.Views;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewRepository extends JpaRepository<Views, Long> {

    @Query("SELECT v FROM Views v WHERE v.member.id = :memberId AND v.chapter.id = :chapterId")
    Views findByMemberIdAndChapterId(@Param("memberId") Long memberId, @Param("chapterId") Long chapterId);


    @Query("SELECT new com.project.novel.dto.BookListDto(b.id, b.bookName, b.bookImage, m.nickname, c.id) " +
            "FROM Views v JOIN v.chapter c JOIN c.book b JOIN b.member m " +
            "WHERE v.member.id = :userId AND v.updatedAt IN " +
            "(SELECT MAX(v2.updatedAt) FROM Views v2 WHERE v2.member.id = :userId GROUP BY v2.chapter.book.id) " +
            "ORDER BY v.updatedAt DESC")
    Slice<BookListDto> findRecentlyViewedBooks(@Param("userId") Long userId, Pageable pageable);
}
