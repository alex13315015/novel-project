package com.project.novel.repository;

import com.project.novel.entity.View;
import com.project.novel.entity.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ViewRepository extends JpaRepository<View, Long> {

}
