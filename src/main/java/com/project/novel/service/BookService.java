package com.project.novel.service;

import com.project.novel.dto.book.BookSaveDto;
import com.project.novel.dto.CustomUserDetailsDto;
import com.project.novel.repository.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


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

}
