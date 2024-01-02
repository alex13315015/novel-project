package com.project.novel.repository;

import com.project.novel.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member,Integer> {
    Optional<Member> findByUserId(String userId);
    @Query("SELECT count(*) FROM Member m WHERE m.userId = :userId")
    int checkDuplicatedId(@Param("userId") String userId);
}
