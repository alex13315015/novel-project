package com.project.novel.repository.book;

import com.project.novel.dto.book.BookDto;
import com.project.novel.entity.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b where lower(b.bookName) like lower(concat('%', :keyword, '%'))")
    List<Book> findByBookNameContainingIgnoreCase(@Param("keyword") String keyword);

    @Query("select b, COALESCE(SUM(c.hits),0) AS hits, COUNT(bl) AS likes, COUNT(s) AS subscribes, m.name " +
            "from Book b left join b.chapters c left join b.bookLikes bl left join b.subscribes s left join b.member m " +
            "WHERE b IN :books group by b")
    Page<Object[]> test1 (@Param("books") List<Book> books, Pageable pageable);
}
