package com.project.novel.service;

import com.project.novel.dto.BookListDto;
import com.project.novel.entity.Views;
import com.project.novel.repository.ChapterRepository;
import com.project.novel.repository.MemberRepository;
import com.project.novel.repository.ViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ViewService {

    private final ViewRepository viewRepository;
    private final MemberRepository memberRepository;
    private final ChapterRepository chapterRepository;

    public void updateView(Long loggedId, Long chapterId) {
        Views views = viewRepository.findByMemberIdAndChapterId(loggedId, chapterId);

        if (views == null) {
            views = Views.builder()
                    .member(memberRepository.findById(loggedId).orElseThrow(() -> new IllegalArgumentException("해당하는 회원을 찾을 수 없습니다.")))
                    .chapter(chapterRepository.findById(chapterId).orElseThrow(() -> new IllegalArgumentException("해당하는 챕터를 찾을 수 없습니다.")))
                    .build();
            viewRepository.save(views);
        } else {
            views.updateViews();
        }
    }

    public Slice<BookListDto> recentViewList(Long loggedId, Pageable pageable) {
        return viewRepository.findRecentlyViewedBooks(loggedId, pageable);
    }

}
