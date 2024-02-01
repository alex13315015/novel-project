package com.project.novel.service;

import com.project.novel.entity.Member;
import com.project.novel.entity.Book;
import com.project.novel.entity.Chapter;
import com.project.novel.enums.AgeRating;
import com.project.novel.enums.Genre;
import com.project.novel.repository.MemberRepository;
import com.project.novel.repository.ViewRepository;
import com.project.novel.repository.BookRepository;
import com.project.novel.repository.ChapterRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(value = false)
class BookServiceTest {

    @Autowired
    private BookService bookService;
    @Autowired
    private  BookRepository bookRepository;
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private ViewRepository viewRepository;

    @Autowired
    MemberRepository memberRepository;
    @Test
    public void test(){
        Page<Object[]> page = bookRepository.test1();


    }




}