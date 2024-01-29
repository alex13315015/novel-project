package com.project.novel.repository;

import com.project.novel.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b where lower(b.bookName) like lower(concat('%', :keyword, '%'))")
    List<Book> findByBookNameContainingIgnoreCase(@Param("keyword") String keyword);

    @Query("SELECT b, COALESCE(SUM(c.hits), 0) AS hits, COUNT(bl) AS likes, COUNT(s) AS subscribes, m.name " +
            "FROM Book b " +
            "LEFT JOIN BookLikes bl on b = bl.book " +
            "LEFT JOIN Subscribe s on b = s.book " +
            "LEFT JOIN Member m on m = b.member " +
            "LEFT JOIN Chapter c on b = c.book " +
            "WHERE b IN :books " +
            "GROUP BY b")
    Page<Object[]> findBookInfoListPage(@Param("books") List<Book> books, Pageable pageable);


    @Query("select b from Book b where b.createdDate > :sevenDaysAgo ORDER BY createdDate DESC")
    Page<Book> findByCreatedDateAfter(@Param("sevenDaysAgo")LocalDateTime sevenDaysAgo, PageRequest pageRequest);


    @Query("select b " +
            "from Book b " +
            "left join Chapter c on c.book = b " +
            "left join View v on v.chapter = c " +
            "where v.createdDate > :sevenDaysAgo GROUP BY b ORDER BY COUNT(v) DESC")
    Page<Book> findBookPopularityOfWeek(@Param("sevenDaysAgo")LocalDateTime sevenDaysAgo, PageRequest pageRequest);
}
