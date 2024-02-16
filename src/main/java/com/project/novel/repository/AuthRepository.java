package com.project.novel.repository;

import com.project.novel.entity.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Authentication,Long> {
    Optional<Authentication> findByRandomCodeAndUserEmail(String randomCode,String Email);


}
