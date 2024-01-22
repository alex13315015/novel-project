package com.project.novel.service;

import com.project.novel.dto.ChapterReviewDto;
import com.project.novel.dto.ChapterReviewUploadDto;
import com.project.novel.entity.Chapter;
import com.project.novel.entity.ChapterReview;
import com.project.novel.entity.Member;
import com.project.novel.repository.ChapterRepository;
import com.project.novel.repository.ChapterReviewRepository;
import com.project.novel.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ChapterReviewService {

    private final ChapterReviewRepository chapterReviewRepository;
    private final ChapterRepository chapterRepository;
    private final MemberRepository memberRepository;

    public void createChapterReview(ChapterReviewUploadDto chapterReviewUploadDto, Long chapterId, Long memberId) {
        Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 챕터를 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 회원을 찾을 수 없습니다."));
        ChapterReview chapterReview = ChapterReview.builder()
                .chapter(chapter)
                .member(member)
                .comments(chapterReviewUploadDto.getComments())
                .build();
        chapterReviewRepository.save(chapterReview);
    }

    public Page<ChapterReviewDto> getChapterReviewList(Long chapterId, Pageable pageable) {
        return chapterReviewRepository.findAllByChapterId(chapterId, pageable);

    }

    public void deleteChapterReview(Long reviewId, Long loggedId) {
        ChapterReview chapterReview = chapterReviewRepository.findById(reviewId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 리뷰를 찾을 수 없습니다."));
        if (!Objects.equals(chapterReview.getMember().getId(), loggedId)) {
            throw new IllegalArgumentException("해당 리뷰를 삭제할 권한이 없습니다.");
        }
        chapterReviewRepository.delete(chapterReview);
    }
}
