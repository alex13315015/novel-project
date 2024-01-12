package com.project.novel.repository.book;

import com.project.novel.dto.book.BookDto;
import com.project.novel.entity.book.Book;
import com.project.novel.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
//    @Query("select new com.project.novel.dto.book.BookDto(b.id, b.bookName, COUNT(s), m.username, SUM(c.hit), SUM(c.likes), b.lastModifiedDate) "
//            + "from Book b join b.member m join b.chapterList c join b.subscribeList s GROUP BY b.id order by "
//            + "CASE WHEN :sortType = 'hit' THEN SUM(c.hit) END DESC, "
//            + "CASE WHEN :sortType = 'likes' THEN SUM(c.likes) END DESC, "
//            + "CASE WHEN :sortType = 'subscribes' THEN COUNT(s) END DESC, "
//            + "CASE WHEN :sortType = 'modifiedDate' THEN b.lastModifiedDate END DESC")
//    List<BookDto> findAllBookList(@Param("sortType") String sortType);

//    @Query("select b")
//    List<BookDto> findAllSortBookList(@Param("sortType") String sortType, @Param("Search") String search);


//    private Long id;
//    private String bookName;
//    private Genre genre;
//    private String author;
//    private Integer ageRating;
//    private String bookIntro;
//    private Long subscribes;
//    private Long hit;
//    private Long likes;
//    private String bookImage;
//    private LocalDateTime createdDate;

    @Query("select new com.project.novel.dto.book.BookDto(b.id, b.bookName, b.genre, m.name, b.ageRating, b.bookIntro, COUNT(s), SUM(c.hits), COUNT(bl), b.bookImage, b.createdDate) " +
            "from Book b join b.member m " +
            "left join Subscribe s on s.book = b " +
            "left join Chapter c on c.book = b " +
            "left join BookLikes bl on bl.book = b " +
            "WHERE (:bookSearch IS NULL OR b.bookName LIKE %:bookSearch%) " +
            "GROUP BY b.id " +
            "order by " +
            "CASE WHEN :sortType = 'hit' THEN SUM(c.hits) END DESC, " +
            "CASE WHEN :sortType = 'likes' THEN COUNT(bl) END DESC, " +
            "CASE WHEN :sortType = 'subscribes' THEN COUNT(s) END DESC, " +
            "CASE WHEN :sortType = 'createdDateDesc' THEN b.createdDate END DESC, " +
            "CASE WHEN :sortType = 'createdDateAsc' THEN b.createdDate END ASC")
    List<BookDto> findAllBookList(@Param("bookSearch") String bookSearch, @Param("sortType") String sortType);
}
