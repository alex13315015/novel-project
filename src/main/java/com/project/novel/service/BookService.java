package com.project.novel.service;

import com.project.novel.dto.BookDto;
import com.project.novel.dto.BookSaveDto;
import com.project.novel.dto.CustomUserDetailsDto;
import com.project.novel.entity.book.Book;
import com.project.novel.repository.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;


    public void saveBook(BookSaveDto bookSaveDto){

        CustomUserDetailsDto userDetails =
                (CustomUserDetailsDto) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        bookRepository.save(bookSaveDto.toEntity(userDetails.getMember()));
    }

    public List<Book> findAllBook(){
        return bookRepository.findAllBookList();
    }












//    public List<BookDto> findAllBook(String sortType){
//
//        List<BookDto> books = new ArrayList<>();
//
//        books.add(new BookDto(1L, "왕좌의 게임", 1L, "진모리", 12L, 12L, LocalDateTime.now() ));
//        books.add(new BookDto(1L, "왕좌의 게임", 1L, "진모리", 12L, 12L, LocalDateTime.now() ));
//        books.add(new BookDto(1L, "왕좌의 게임", 1L, "진모리", 12L, 12L, LocalDateTime.now() ));
//        books.add(new BookDto(1L, "왕좌의 게임", 1L, "진모리", 12L, 12L, LocalDateTime.now() ));
//        books.add(new BookDto(1L, "왕좌의 게임", 1L, "진모리", 12L, 12L, LocalDateTime.now() ));
//
//        return books;
//    }
}
