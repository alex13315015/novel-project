package com.project.novel.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.novel.dto.ChapterDetailDto;
import com.project.novel.dto.ChapterDto;
import com.project.novel.dto.ChapterUploadDto;
import com.project.novel.entity.Book;
import com.project.novel.entity.Chapter;
import com.project.novel.repository.BookRepository;
import com.project.novel.repository.ChapterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChapterService {

    private final ChapterRepository chapterRepository;
    private final BookRepository bookRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public void writeChapter(ChapterUploadDto chapterUploadDto, Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 책을 찾을 수 없습니다.")
        );
        book.chapterUpdated(); // 책의 최근 업데이트 날짜를 현재 시간으로 업데이트
        Chapter chapter = chapterUploadDto.toEntity(book);
        chapterRepository.save(chapter);
    }


    public Page<ChapterDto> getChapterList(Long bookId, Pageable pageable) {
        return chapterRepository.findAllByBookId(bookId, pageable);
    }

    @Transactional
    public ChapterDetailDto getChapterDetail(Long chapterId, String userId) {
        String chapterKey = "chapter:detail:" + chapterId;
        String viewKey = "chapter:view:" + chapterId + ":" + userId;

        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        Object cachedData = ops.get(chapterKey);

        ChapterDetailDto chapterDetailDto = null;
        if (cachedData instanceof LinkedHashMap) {
            ObjectMapper mapper = new ObjectMapper();
            chapterDetailDto = mapper.convertValue(cachedData, ChapterDetailDto.class);
        } else if (cachedData instanceof ChapterDetailDto) {
            chapterDetailDto = (ChapterDetailDto) cachedData;
        }

        if (chapterDetailDto == null) { // Redis에 캐싱된 정보가 없는 경우 DB에서 정보를 가져옴
            log.info("챕터 상세 정보를 Redis에서 가져오지 못했습니다. DB에서 가져옵니다.");
            Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(
                    () -> new IllegalArgumentException("해당하는 챕터를 찾을 수 없습니다.")
            );

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedCreatedAt = chapter.getCreatedAt().format(formatter);

            chapterDetailDto = ChapterDetailDto.builder()
                    .chapterId(chapter.getId())
                    .bookId(chapter.getBook().getId())
                    .title(chapter.getTitle())
                    .contents(chapter.getContents())
                    .hits(chapter.getHits())
                    .createdAt(formattedCreatedAt)
                    .build();

            ops.set(chapterKey, chapterDetailDto, 1, TimeUnit.MINUTES); // 챕터 상세 정보를 Redis에 1분 동안 캐싱
        }

        if (ops.get(viewKey) == null) { // 해당 사용자가 이 챕터를 처음 조회하는 경우
            log.info("해당 사용자가 이 챕터를 처음 조회했습니다.");
            ops.set(viewKey, true, 1, TimeUnit.MINUTES); // 해당 사용자가 이 챕터를 조회했음을 표시하고, 1분 후에 자동 삭제
            ops.increment("chapter:hits:" + chapterId, 1); // 조회수 증가
        }

        return chapterDetailDto;
    }

    @Transactional
    public void deactivateChapter(Long chapterId, Long loggedId) {
        Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 챕터를 찾을 수 없습니다.")
        );
        if(chapter.getBook().getMember().getId().equals(loggedId)) {
            chapter.deactivate();
        } else {
            throw new IllegalArgumentException("해당 챕터의 작성자가 아닙니다.");
        }
    }

    public ChapterUploadDto modifyChapter(Long chapterId, Long memberId) {
        Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 챕터를 찾을 수 없습니다.")
        );
        if(chapter.getBook().getMember().getId().equals(memberId)) {
            return ChapterUploadDto.builder()
                    .title(chapter.getTitle())
                    .contents(chapter.getContents())
                    .price(chapter.getPrice())
                    .bookId(chapter.getBook().getId())
                    .build();
        } else {
            throw new IllegalArgumentException("해당 챕터의 작성자가 아닙니다.");
        }
    }

    @Transactional
    public void updateChapter(ChapterUploadDto chapterUploadDto, Long chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new IllegalArgumentException("해당 챕터가 없습니다."));
        chapter.update(chapterUploadDto.getTitle(), chapterUploadDto.getContents(), chapterUploadDto.getPrice());
        chapterRepository.save(chapter);

        // 수정된 챕터 정보를 Redis에 업데이트
        String chapterKey = "chapter:detail:" + chapterId;
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedCreatedAt = chapter.getCreatedAt().format(formatter);

        ChapterDetailDto chapterDetailDto = ChapterDetailDto.builder()
                .chapterId(chapter.getId())
                .bookId(chapter.getBook().getId())
                .title(chapter.getTitle())
                .contents(chapter.getContents())
                .hits(chapter.getHits())
                .createdAt(formattedCreatedAt)
                .build();

        ops.set(chapterKey, chapterDetailDto, 1, TimeUnit.MINUTES); // 챕터 상세 정보를 Redis에 1분 동안 캐싱
    }
}
