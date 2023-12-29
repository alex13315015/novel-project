package com.project.novel.repository.book;

import com.project.novel.dto.BookDto;
import com.project.novel.entity.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select new com.project.novel.dto.BookDto(b.id, b.bookName, b.subscribes, m.username, SUM(c.hit), SUM(c.likes), b.lastModifiedDate) "
            + "from Book b join b.member m join chapterList c GROUP BY b.id order by "
            + "CASE WHEN :sortType = 'hit' THEN SUM(c.hit) END DESC, "
            + "CASE WHEN :sortType = 'hit' THEN SUM(c.likes) END DESC, "
            + "CASE WHEN :sortType = 'subscribes' THEN b.subscribes END DESC, "
            + "CASE WHEN :sortType = 'modifiedDate' THEN b.lastModifiedDate END DESC")
    List<BookDto> findAllBookList(@Param("sortType") String sortType);

}
