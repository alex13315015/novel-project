package com.project.novel.service;

import com.project.novel.dto.ChapterDetailDto;
import com.project.novel.dto.ChapterDto;
import com.project.novel.dto.ChapterUploadDto;
import com.project.novel.entity.Book;
import com.project.novel.entity.Chapter;
import com.project.novel.repository.BookRepository;
import com.project.novel.repository.ChapterRepository;
import jakarta.servlet.http.HttpSession;
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
    private final HttpSession httpSession;

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

        String sessionId = httpSession.getId(); // 세션 ID를 가져옴
        String viewKey = "chapter:view:" + chapterId + ":" + sessionId;

        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        if (ops.get(viewKey) == null) { // 해당 세션에서 이 챕터를 처음 조회하는 경우
            ops.set(viewKey, true); // 해당 세션에서 이 챕터를 조회했음을 표시
            ops.increment("chapter:hits:" + chapterId, 1); // 조회수 증가
        }

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

    @Transactional
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
                log.info("챕터 찾는 쿼리 발송 ㅇ");
                Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(
                        () -> new IllegalArgumentException("해당하는 챕터를 찾을 수 없습니다.")
                );
                log.info("hit 업데이트 해주는 쿼리 발송 ㅇ");
                chapter.updateHits(hits);
                log.info("save 해주는 쿼리 발송 ㅇ");
                chapterRepository.save(chapter);
            }

            redisTemplate.delete(key);
        }
    }

}
