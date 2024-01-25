package com.project.novel.service;

import com.project.novel.entity.Book;
import com.project.novel.entity.Chapter;
import com.project.novel.repository.BookRepository;
import com.project.novel.repository.ChapterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class BatchService {

    private final BookRepository bookRepository;
    private final ChapterRepository chapterRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    // 1분 마다 비활성화된 책 삭제
    @Scheduled(cron = "0 0/1 * * * *")
    public void deleteDeactivatedBooks() {
        log.info("deleteDeactivatedBooks() 실행");
        List<Book> deactivatedBooks = bookRepository.findByIsActive();
        bookRepository.deleteAll(deactivatedBooks);
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void deleteDeactivatedChapters() {
        log.info("deleteDeactivatedChapters() 실행");
        List<Chapter> deactivatedChapters = chapterRepository.findByIsActive();
        chapterRepository.deleteAll(deactivatedChapters);
    }

    // 1분 마다 조회수 업데이트
    @Scheduled(cron = "0 0/1 * * * *")
    public void updateChapterViews() {
        log.info("updateChapterViews() 실행");
        Set<String> keys = redisTemplate.keys("chapter:view:*:*");
        if (keys == null) return;

        Map<Long, Long> chapterHits = new HashMap<>();
        for (String key : keys) { // 조회수를 증가시킬 챕터의 id와 증가시킬 조회수를 Map에 저장
            Long chapterId = Long.parseLong(key.split(":")[2]);
            chapterHits.put(chapterId, chapterHits.getOrDefault(chapterId, 0L) + 1); // 조회수 증가
        }

        for (Map.Entry<Long, Long> entry : chapterHits.entrySet()) {
            Long chapterId = entry.getKey();
            Long hits = entry.getValue();
            chapterRepository.updateHits(chapterId, hits);
        }

        redisTemplate.delete(keys);

        // 챕터 상세 정보를 나타내는 키를 삭제
        Set<String> detailKeys = redisTemplate.keys("chapter:detail:*");
        if (detailKeys != null) {
            redisTemplate.delete(detailKeys);
        }
    }
}
