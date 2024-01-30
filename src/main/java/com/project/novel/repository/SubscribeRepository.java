package com.project.novel.repository;

import com.project.novel.entity.Book;
import com.project.novel.entity.Member;
import com.project.novel.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    Subscribe findByBookAndMember(Book book, Member loggedMember);
    boolean existsByBookAndMember(Book book, Member loggedMember);
    Integer countByBook(Book book);
}
