package com.project.novel.repository;

import com.project.novel.dto.BookListDto;
import com.project.novel.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT new com.project.novel.dto.BookListDto(b.id, b.bookName, b.bookImage, m.nickname) " +
            "FROM Book b JOIN b.member m " +
            "WHERE m.id = :loggedId AND b.isActive = true " +
            "ORDER BY CASE WHEN :order = 'Oldest' THEN b.createdAt END ASC, " +
            "CASE WHEN :order = 'Update' THEN b.createdAt END DESC")
    List<BookListDto> findAllByMemberId(@Param("loggedId") Long loggedId, @Param("order") String order);

    @Query("SELECT b FROM Book b WHERE b.isActive = false")
    List<Book> findByIsActive();

}
