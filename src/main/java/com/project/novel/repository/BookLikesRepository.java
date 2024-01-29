package com.project.novel.repository;

import com.project.novel.entity.Book;
import com.project.novel.entity.BookLikes;
import com.project.novel.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookLikesRepository extends JpaRepository<BookLikes, Long> {

    @Query("SELECT COUNT(bl) FROM BookLikes bl WHERE bl.book.id = :book_id")
    int likeCount(@Param("book_id") Long id);

    @Query("SELECT COUNT(bl) FROM BookLikes bl WHERE bl.book.id = :bookId")
    int countByBookId(@Param("bookId") Long bookId);

    @Query("SELECT CASE WHEN COUNT(bl) > 0 THEN true ELSE false END FROM BookLikes bl WHERE bl.book.id = :bookId AND bl.member.id = :memberId")
    boolean existsByBookIdAndMemberId(@Param("bookId") Long bookId, @Param("memberId") Long memberId);


    @Query("SELECT bl FROM BookLikes bl WHERE bl.book.id = :bookId AND bl.member.id = :memberId")
    BookLikes findByBookIdAndMemberId(@Param("bookId") Long bookId, @Param("memberId") Long memberId);

}
