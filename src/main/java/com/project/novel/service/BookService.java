package com.project.novel.service;

import com.project.novel.dto.BookDto;
import com.project.novel.repository.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookDto> findAllBook(String sortType){
            return bookRepository.findAllBookList(sortType);
    }
}
