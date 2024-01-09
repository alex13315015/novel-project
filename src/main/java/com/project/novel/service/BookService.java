package com.project.novel.service;

import com.project.novel.dto.BookDto;
import com.project.novel.repository.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public List<BookDto> findAllBook(String sortType){

        List<BookDto> books = new ArrayList<>();

        books.add(new BookDto(1L, "왕좌의 게임", 1L, "진모리", 12L, 12L, LocalDateTime.now() ));
        books.add(new BookDto(1L, "왕좌의 게임", 1L, "진모리", 12L, 12L, LocalDateTime.now() ));
        books.add(new BookDto(1L, "왕좌의 게임", 1L, "진모리", 12L, 12L, LocalDateTime.now() ));
        books.add(new BookDto(1L, "왕좌의 게임", 1L, "진모리", 12L, 12L, LocalDateTime.now() ));
        books.add(new BookDto(1L, "왕좌의 게임", 1L, "진모리", 12L, 12L, LocalDateTime.now() ));

        return books;
    }
}
