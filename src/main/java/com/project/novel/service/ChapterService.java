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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Scheduled(cron = "0 0/1 * * * *")
    public void updateChapterViews() {
        log.info("updateChapterViews() 실행");
        Set<String> keys = redisTemplate.keys("chapter:view:*:*");
        if (keys == null) return;

        Map<Long, Long> chapterHits = new HashMap<>();
        for (String key : keys) { // 조회수를 증가시킬 챕터의 id와 증가시킬 조회수를 Map에 저장
            Long chapterId = Long.parseLong(key.split(":")[2]); // key의 형식: chapter:view:chapterId:userId
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
