package com.project.novel.service;


import com.project.novel.dto.book.BookInfoDto;
import com.project.novel.dto.book.BookSummaryDto;
import com.project.novel.entity.View;
import com.project.novel.entity.book.Book;
import com.project.novel.repository.ViewRepository;
import com.project.novel.repository.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IndexService {

    private final BookRepository bookRepository;

    public List<BookSummaryDto> getNewBookOfWeek(){
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        Page<Book> books = bookRepository.findByCreatedDateAfter(sevenDaysAgo,
                PageRequest.of(0, 5));
        
        return toSummaryDto(books);
    }

    public List<BookSummaryDto> getPopularityBookOfWeek(){
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        Page<Book> books = bookRepository.findBookPopularityOfWeek(sevenDaysAgo,
                PageRequest.of(0, 5));

        return toSummaryDto(books);
    }

    public List<BookSummaryDto> toSummaryDto(Page<Book> books){
        return books.stream()
                .map(BookSummaryDto::new)
                .toList();
    }

}
