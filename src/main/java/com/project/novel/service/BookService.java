package com.project.novel.service;

import com.project.novel.dto.LibraryPageDto;
import com.project.novel.dto.book.BookDto;
import com.project.novel.dto.book.BookSaveDto;
import com.project.novel.dto.CustomUserDetailsDto;
import com.project.novel.entity.book.Book;
import com.project.novel.repository.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.LinkedList;
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


    public List<BookDto> getBookInfoList(List<Object[]> infoBooks){
        List<BookDto> bookInfoList = new LinkedList<>();
        for (Object[] array : infoBooks) {
            Book book = (Book) array[0];
            Long hit = (Long)array[1];
            Long bookLikes = (Long)array[2];
            Long subscribes = (Long)array[3];
            String author = (String)array[4];
            bookInfoList.add(new BookDto(book, hit, bookLikes, subscribes, author));
            System.out.println("book = " + book);
        }
        return bookInfoList;
    }

    public List<BookDto> test(String sortType, String bookSearch, int pageNum, Model model){
        List<Book> bookList = bookRepository.findByBookNameContainingIgnoreCase(bookSearch);
        Sort sort = getSortType(sortType);
        Pageable pageable = PageRequest.of(pageNum - 1, 5, sort);
        Page<Object[]> page = bookRepository.test1(bookList, pageable);
        LibraryPageDto pageDto = new LibraryPageDto(page);
        model.addAttribute("pageDto", pageDto);
        List<Object[]> content = page.getContent();
        return getBookInfoList(content);
    }

    public Sort getSortType(String sortType){
        if (sortType.equals("createdDateDesc")) {
            return Sort.by("createdDate").descending();
        }
        if (sortType.equals("createdDateAsc")) {
            return Sort.by("createdDate").ascending();
        }
        return Sort.by(sortType).descending();
    }

}
