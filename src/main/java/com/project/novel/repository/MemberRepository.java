package com.project.novel.repository;

import com.project.novel.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByUserId(String userId);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByPassword(String password);

    Optional<Member> findByUserIdAndEmail(String userId, String email);
    void deleteById(Long id);
}
