package com.project.novel.service;

import com.project.novel.dto.LibraryPageDto;
import com.project.novel.dto.BookInfoDto;
import com.project.novel.entity.Book;
import com.project.novel.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final BookRepository bookRepository;

    public List<BookInfoDto> test(String sortType, String bookSearch, int pageNum, Model model){
        List<Book> bookList = bookRepository.findByBookNameContainingIgnoreCase(bookSearch);

        if(pageNum < 1){
            pageNum = 1;
        }

        Page<Object[]> page = bookRepository.findBookInfoListPage(bookList,
                PageRequest.of(pageNum - 1, 10, getSortType(sortType)));
        LibraryPageDto pageDto = new LibraryPageDto(page);

        if(page.getTotalPages() < pageNum){
            pageDto.setPage(page.getTotalPages());
            page = bookRepository.findBookInfoListPage(bookList,
                    PageRequest.of((int)pageDto.getPage() - 1, 10, getSortType(sortType)));
        }

        model.addAttribute("pageDto", pageDto);
        List<Object[]> content = page.getContent();
        return getBookInfoList(content);
    }

    public List<BookInfoDto> getBookInfoList(List<Object[]> infoBooks) {
        return infoBooks.stream()
                .map(array -> {
                    Book book = (Book) array[0];
                    long hits = (Long) array[1];
                    long likes = (Long) array[2];
                    long subscribes = (Long) array[3];
                    String author = (String) array[4];
                    return new BookInfoDto(book, hits, likes, subscribes, author);
                })
                .collect(Collectors.toList());
    }

    public Sort getSortType(String sortType){
        if (sortType.equals("createdDateDesc")) {
            return Sort.by("createdAt").descending();
        }
        if (sortType.equals("createdDateAsc")) {
            return Sort.by("createdAt").ascending();
        }
        return Sort.by(sortType).descending();
    }
}
