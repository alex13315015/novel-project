package com.project.novel.repository;

import com.project.novel.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByUserId(String userId);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByPassword(String password);
    Optional<Member> findByUserIdAndEmail(String userId, String email);

    @Query(value = "DELETE FROM MEMBER WHERE id = :id" ,nativeQuery = true)
    void deleteMember(@Param("id") Long id);




    /*@Query("SELECT COUNT(*) FROM Member m WHERE m.userId = :userId")
    int checkDuplicatedId(@Param("userId") String userId);

    @Query("SELECT COUNT(*) FROM Member m WHERE m.email = :email")
    int checkDuplicatedEmail(@Param("email") String email);*/
}
