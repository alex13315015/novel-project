package com.project.novel.api;

import com.project.novel.dto.ChapterReviewDto;
import com.project.novel.dto.ChapterReviewUploadDto;
import com.project.novel.dto.CustomUserDetails;
import com.project.novel.service.ChapterReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ChapterReviewApiController {

    private final ChapterReviewService chapterReviewService;

    @PostMapping("/chapter/{chapterId}/review")
    public void createChapterReview(ChapterReviewUploadDto chapterReviewUploadDto,
                                    @PathVariable(name="chapterId") Long chapterId,
                                    @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        chapterReviewService.createChapterReview(chapterReviewUploadDto, chapterId, customUserDetails.getLoggedMember().getId());
    }

    @GetMapping("/chapter/{chapterId}/review")
    public Page<ChapterReviewDto> getChapterReview(@PathVariable(name="chapterId") Long chapterId,
                                                   @PageableDefault Pageable pageable) {
        return chapterReviewService.getChapterReviewList(chapterId, pageable);
    }

    @DeleteMapping("/review/{reviewId}")
    public void deleteChapterReview(@PathVariable(name="reviewId") Long reviewId,
                                    @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        chapterReviewService.deleteChapterReview(reviewId, customUserDetails.getLoggedMember().getId());
    }

}
