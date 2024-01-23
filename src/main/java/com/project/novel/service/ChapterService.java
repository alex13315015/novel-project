package com.project.novel.service;

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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChapterService {

    private final ChapterRepository chapterRepository;
    private final BookRepository bookRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public void writeChapter(ChapterUploadDto chapterUploadDto, Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 책을 찾을 수 없습니다.")
        );
        Chapter chapter = chapterUploadDto.toEntity(book);
        chapterRepository.save(chapter);
    }


    public List<ChapterDto> getAllChapter(Long bookId, String order) {

        return chapterRepository.findAllByBookId(bookId, order);
    }

    @Transactional
    public ChapterDetailDto getChapterDetail(Long chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 챕터를 찾을 수 없습니다.")
        );

        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.increment("chapter:hits:" + chapterId, 1);
        log.info("redis 조회수: " + ops.get("chapter:hits:" + chapterId));
        Number hitsNumber = (Number) ops.get("chapter:hits:" + chapterId);
        Long hits = hitsNumber != null ? hitsNumber.longValue() : chapter.getHits();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedCreatedAt = chapter.getCreatedAt().format(formatter);
        return ChapterDetailDto.builder()
                .chapterId(chapter.getId())
                .bookId(chapter.getBook().getId())
                .title(chapter.getTitle())
                .contents(chapter.getContents())
                .hits(hits)
                .createdAt(formattedCreatedAt)
                .build();
    }
    // 1분 마다 업데이트
    @Scheduled(cron = "0 0/1 * * * *")
    public void updateChapterViews() {
        log.info("updateChapterViews() 실행");
        Set<String> keys = redisTemplate.keys("chapter:hits:*");
        if (keys == null) return;

        ValueOperations<String, Object> ops = redisTemplate.opsForValue();

        for (String key : keys) {
            Long chapterId = Long.parseLong(key.split(":")[2]);
            Number hitsNumber = (Number) ops.get(key);
            Long hits = hitsNumber != null ? hitsNumber.longValue() : null;

            if (hits != null) {
                Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(
                        () -> new IllegalArgumentException("해당하는 챕터를 찾을 수 없습니다.")
                );
                chapter.updateHits(hits);
                chapterRepository.save(chapter);
            }

            redisTemplate.delete(key);
        }
    }

}
