package com.project.novel.service;


import com.project.novel.dto.BookSummaryDto;
import com.project.novel.entity.Book;
import com.project.novel.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
