package com.project.novel.service;

import com.project.novel.dto.BookListDto;
import com.project.novel.entity.Book;
import com.project.novel.entity.Member;
import com.project.novel.entity.Subscribe;
import com.project.novel.repository.BookRepository;
import com.project.novel.repository.MemberRepository;
import com.project.novel.repository.SubscribeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final SubscribeRepository subscribeRepository;

    @Transactional
    public void subscribe(Long bookId, Long loggedId) {

        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 책을 찾을 수 없습니다.")
        );
        Member loggedMember = memberRepository.findById(loggedId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 회원을 찾을 수 없습니다.")
        );

        Subscribe subscribe = Subscribe.builder()
                .book(book)
                .member(loggedMember)
                .build();
        subscribeRepository.save(subscribe);
    }


    @Transactional
    public void unsubscribe(Long bookId, Long loggedId) {

        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 책을 찾을 수 없습니다.")
        );
        Member loggedMember = memberRepository.findById(loggedId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 회원을 찾을 수 없습니다.")
        );

        Subscribe subscribe = subscribeRepository.findByBookAndMember(book, loggedMember);
        subscribeRepository.delete(subscribe);

    }

    @Transactional
    public boolean isSubscribed(Long bookId, Long loggedId) {

        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 책을 찾을 수 없습니다.")
        );
        Member loggedMember = memberRepository.findById(loggedId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 회원을 찾을 수 없습니다.")
        );

        return subscribeRepository.existsByBookAndMember(book, loggedMember);
    }

    @Transactional
    public Integer subscribeCount(Long bookId) {

        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 책을 찾을 수 없습니다.")
        );

        return subscribeRepository.countByBook(book);
    }

    public Slice<BookListDto> getMySubscribeList(Long loggedId, Pageable pageable) {
        return subscribeRepository.findMySubscribeList(loggedId, pageable);
    }
}
