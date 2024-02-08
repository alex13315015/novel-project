package com.project.novel.repository;


import com.project.novel.enums.BookGenre;
import org.springframework.data.domain.PageRequest;
import com.project.novel.dto.BookListDto;
import com.project.novel.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT new com.project.novel.dto.BookListDto(b.id, b.bookName, b.bookImage, m.nickname) " +
            "FROM Book b JOIN b.member m " +
            "WHERE m.id = :loggedId AND b.isActive = true")
    Slice<BookListDto> findMyBookList(@Param("loggedId") Long loggedId, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.isActive = false")
    List<Book> findByIsActive();

//    @Query("select b from Book b where lower(b.bookName) like lower(concat('%', :keyword, '%'))")
//    List<Book> findByBookNameContainingIgnoreCase(@Param("keyword") String keyword);

    @Query("SELECT b, COALESCE(SUM(c.hits), 0) AS hits, COUNT(DISTINCT bl) AS likes, COUNT(DISTINCT s) AS subscribes, m.nickname " +
            "FROM Book b " +
            "LEFT JOIN BookLikes bl on b = bl.book " +
            "LEFT JOIN Subscribe s on b = s.book " +
            "LEFT JOIN Member m on m = b.member " +
            "LEFT JOIN Chapter c on b = c.book " +
            "WHERE lower(b.bookName) like lower(concat('%', :keyword, '%')) and (:genre IS NULL OR b.bookGenre = :genre) " +
            "GROUP BY b")
    Page<Object[]> findBookInfoListPage(String keyword, @Param("genre") BookGenre bookGenre, Pageable pageable);


    @Query("select b from Book b where b.createdAt > :sevenDaysAgo ORDER BY b.createdAt DESC")
    Page<Book> findByCreatedDateAfter(@Param("sevenDaysAgo")LocalDateTime sevenDaysAgo, PageRequest pageRequest);


    @Query("select b " +
            "from Book b " +
            "left join Chapter c on c.book = b " +
            "left join Views v on v.chapter = c " +
            "where v.updatedAt > :sevenDaysAgo GROUP BY b ORDER BY COUNT(v) DESC")
    Page<Book> findBookPopularityOfWeek(@Param("sevenDaysAgo")LocalDateTime sevenDaysAgo, PageRequest pageRequest);

}
