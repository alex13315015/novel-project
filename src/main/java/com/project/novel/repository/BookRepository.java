package com.project.novel.repository;

import com.project.novel.dto.BookListDto;
import com.project.novel.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT new com.project.novel.dto.BookListDto(b.id, b.bookName, b.bookImage, m.nickname) " +
            "FROM Book b JOIN b.member m " +
            "WHERE m.id = :loggedId AND b.isActive = true")
    Page<BookListDto> findAllByMemberId(@Param("loggedId") Long loggedId, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.isActive = false")
    List<Book> findByIsActive();

}
