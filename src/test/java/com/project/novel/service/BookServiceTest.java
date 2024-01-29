package com.project.novel.service;

import com.project.novel.entity.Member;
import com.project.novel.entity.View;
import com.project.novel.entity.book.Book;
import com.project.novel.entity.Chapter;
import com.project.novel.enums.AgeRating;
import com.project.novel.enums.Genre;
import com.project.novel.repository.MemberRepository;
import com.project.novel.repository.ViewRepository;
import com.project.novel.repository.book.BookRepository;
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
        List<Book> books = bookRepository.findByBookNameContainingIgnoreCase("test");

        for (Book book : books) {
            System.out.println("bookName == " + book.getBookName());
        }
    }



    @Test void test3(){
        List<Book> bookList = bookRepository.findAll();
        Pageable pageable = PageRequest.of(0, 5, Sort.by("hits").descending());
        Page<Object[]> books = bookRepository.findBookInfoListPage(bookList, pageable);

        for (Object[] book : books) {
            Book book1 = (Book) book[0];
            System.out.println("book1.getBookName() = " + book1.getBookName());
        }

    }

    @Test void test4(){
        Optional<Member> member = memberRepository.findById(1L);
        for(int i = 0; i < 200; i++){
            Book book = new Book("jin" + i, "intro" + i, AgeRating.AGE_19, null, Genre.FANTASY, member.get());
            bookRepository.save(book);
        }
    }

    @Test void test2(){
        Optional<Member> member = memberRepository.findById(1L);
        Optional<Book> book = bookRepository.findById(55L);
        Chapter chapter = new Chapter(book.get(), "test2", "test2", 0L);
        chapterRepository.save(chapter);
        View view = new View(member.get(), chapter);
        viewRepository.save(view);
    }

    @Test void test33(){

    }

//    @Test void test5(){
//        Optional<Book> book = bookRepository.findById(2L);
//        Book book1 = book.get();
//
//        List<Chapter> chapters = book1.getChapters();
//
//        for (Chapter chapter : chapters) {
//            System.out.println("chapter.getTitle() = " + chapter.getTitle());
//        }
//
//    }

}