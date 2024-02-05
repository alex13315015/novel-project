package com.project.novel.repository;

import com.project.novel.dto.BookListDto;
import com.project.novel.entity.Book;
import com.project.novel.entity.Member;
import com.project.novel.entity.Subscribe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    Subscribe findByBookAndMember(Book book, Member loggedMember);
    boolean existsByBookAndMember(Book book, Member loggedMember);
    Integer countByBook(Book book);

    @Query("SELECT new com.project.novel.dto.BookListDto(s.book.id, s.book.bookName, s.book.bookImage, s.member.nickname) FROM Subscribe s WHERE s.member.id = :loggedId")
    Slice<BookListDto> findMySubscribeList(@Param("loggedId") Long loggedId, Pageable pageable);
}
