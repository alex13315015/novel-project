package com.project.novel.api;

import com.project.novel.dto.ChapterDto;
import com.project.novel.service.ChapterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ChapterApiController {

    private final ChapterService chapterService;

    @GetMapping("/book/{bookId}/chapters")
    public Page<ChapterDto> getChapterList(@PathVariable(name="bookId") Long bookId,
                                           @PageableDefault(size = 5) Pageable pageable) {
        return chapterService.getChapterList(bookId, pageable);
    }

}
