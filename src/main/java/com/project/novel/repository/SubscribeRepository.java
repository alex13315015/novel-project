package com.project.novel.repository;

import com.project.novel.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    @Modifying
    @Query(value = "INSERT INTO SUBSCRIBE (id,member_id,book_id) VALUES (null,:member_id,:book_id)",nativeQuery = true)
    void subscribe(@Param("member_id") Long member_id, @Param("book_id") Long book_id);

    @Modifying
    @Query(value = "DELETE FROM SUBSCRIBE WHERE member_id = :loggedMemberId and book_id = :urlId", nativeQuery = true)
    void unsubscribe(@Param("loggedMemberId") Long loggedMemberId, @Param("urlId") Long urlId);
}
