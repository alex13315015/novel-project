package com.project.novel.service;

import com.project.novel.dto.BookDto;
import com.project.novel.dto.ChapterDetailDto;
import com.project.novel.dto.ChapterDto;
import com.project.novel.dto.ChapterUploadDto;
import com.project.novel.entity.Book;
import com.project.novel.entity.Chapter;
import com.project.novel.repository.BookRepository;
import com.project.novel.repository.ChapterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChapterService {

    private final ChapterRepository chapterRepository;
    private final BookRepository bookRepository;

    public void write(ChapterUploadDto chapterUploadDto, Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 책을 찾을 수 없습니다.")
        );
        Chapter chapter = chapterUploadDto.toEntity(
                chapterUploadDto.getTitle(),
                chapterUploadDto.getContents(),
                chapterUploadDto.getPrice(),
                book);
        chapterRepository.save(chapter);
    }


    public List<ChapterDto> getAllChapter(Long bookId, String order) {

        return chapterRepository.findAllByBookId(bookId, order);
    }

    public ChapterDetailDto getChapterDetail(Long chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 챕터를 찾을 수 없습니다.")
        );
        chapter.incrementHits();
        chapterRepository.save(chapter);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedCreatedAt = chapter.getCreatedAt().format(formatter);
        return ChapterDetailDto.builder()
                .chapterId(chapter.getId())
                .title(chapter.getTitle())
                .contents(chapter.getContents())
                .hits(chapter.getHits())
                .createdAt(formattedCreatedAt)
                .build();
    }


}
