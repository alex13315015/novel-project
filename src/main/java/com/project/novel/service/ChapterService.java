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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

        // 새로 작성된 챕터의 상세 정보를 Redis에 저장
        String chapterKey = "chapter:detail:" + chapter.getId();
        ChapterDetailDto chapterDetailDto = createChapterDetailDto(chapter);
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set(chapterKey, chapterDetailDto, 1, TimeUnit.MINUTES); // 챕터 상세 정보를 Redis에 1분 동안 캐싱

        // Redis에 저장된 챕터 리스트를 가져와 새로운 챕터의 ID를 추가
        String bookKey = "book:chapters:" + bookId;
        List<Object> chapterIdList = (List<Object>) ops.get(bookKey);
        if (chapterIdList == null) {
            chapterIdList = new ArrayList<>();
        }
        chapterIdList.add(chapter.getId());

        // 업데이트된 챕터 리스트를 Redis에 저장
        ops.set(bookKey, chapterIdList, 1, TimeUnit.MINUTES);
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

        if (chapterDetailDto == null) {
            log.info("챕터 상세 정보를 Redis에서 가져오지 못했습니다. DB에서 가져옵니다.");
            Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(
                    () -> new IllegalArgumentException("해당하는 챕터를 찾을 수 없습니다.")
            );
            chapterDetailDto = createChapterDetailDto(chapter);
            ops.set(chapterKey, chapterDetailDto, 1, TimeUnit.MINUTES); // 챕터 상세 정보를 Redis에 1분 동안 캐싱
        }

        if (ops.get(viewKey) == null) { // 해당 사용자가 이 챕터를 처음 조회하는 경우
            log.info("해당 사용자가 이 챕터를 처음 조회했습니다.");
            ops.set(viewKey, true, 1, TimeUnit.MINUTES); // 해당 사용자가 이 챕터를 조회했음을 표시하고, 1분 후에 자동 삭제
            ops.increment("chapter:hits:" + chapterId, 1); // 조회수 증가
        }

        return chapterDetailDto;
    }

    public List<Object> getChapterList(Long bookId) {
        String bookKey = "book:chapters:" + bookId;
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        List<Object> chapterIdList = (List<Object>) ops.get(bookKey);

        if (chapterIdList == null) {
            log.info("챕터 리스트를 Redis에서 가져오지 못했습니다. DB에서 가져옵니다.");
            List<Chapter> chapterList = chapterRepository.findAllByBookId(bookId);

            chapterIdList = new ArrayList<>(); // redis 리스트 초기화
            for (Chapter chapter : chapterList) {
                chapterIdList.add(chapter.getId());
            }

            ops.set(bookKey, chapterIdList, 1, TimeUnit.MINUTES);
        } else {
            // Redis에서 가져온 데이터 Long 타입으로 변환
            chapterIdList = chapterIdList.stream()
                    .map(obj -> Long.valueOf(obj.toString()))
                    .collect(Collectors.toList());
        }


        return chapterIdList;
    }

    @Transactional
    public ChapterDetailDto getNextChapter(Long currentChapterId, Long bookId, String userId) {
        List<Object> chapterIdList = getChapterList(bookId);
        int currentIndex = chapterIdList.indexOf(currentChapterId);
        log.info("currentIndex: " + currentIndex);

        if (currentIndex < chapterIdList.size() - 1) { // 다음 챕터가 있는 경우
            Long nextChapterId = (Long) chapterIdList.get(currentIndex + 1);
            return getChapterDetail(nextChapterId, userId);
        } else {
            throw new IllegalArgumentException("다음 챕터가 없습니다.");
        }
    }

    @Transactional
    public ChapterDetailDto getPrevChapter(Long currentChapterId, Long bookId, String userId) {
        List<Object> chapterIdList = getChapterList(bookId);
        int currentIndex = chapterIdList.indexOf(currentChapterId);

        if (currentIndex > 0) { // 이전 챕터가 있는 경우
            Long previousChapterId = (Long) chapterIdList.get(currentIndex - 1);
            return getChapterDetail(previousChapterId, userId);
        } else {
            throw new IllegalArgumentException("이전 챕터가 없습니다.");
        }
    }

    @Transactional
    public void deactivateChapter(Long chapterId, Long loggedId) {
        Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 챕터를 찾을 수 없습니다.")
        );
        if(chapter.getBook().getMember().getId().equals(loggedId)) {
            chapter.deactivate();
        } else {
            throw new AccessDeniedException("해당 챕터의 작성자가 아닙니다.");
        }
    }

    // 수정을 위해 챕터 정보를 가져옴
    public ChapterUploadDto getModifiedChapter(Long chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 챕터를 찾을 수 없습니다.")
        );
        return ChapterUploadDto.builder()
                .title(chapter.getTitle())
                .contents(chapter.getContents())
                .price(chapter.getPrice())
                .bookId(chapter.getBook().getId())
                .build();
    }

    @Transactional
    public void modifyChapter(ChapterUploadDto chapterUploadDto, Long chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new IllegalArgumentException("해당 챕터가 없습니다."));
        chapter.update(chapterUploadDto.getTitle(), chapterUploadDto.getContents(), chapterUploadDto.getPrice());
        chapterRepository.save(chapter);

        // 수정된 챕터 정보를 Redis에 업데이트
        String chapterKey = "chapter:detail:" + chapterId;
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();

        ops.set(chapterKey, createChapterDetailDto(chapter), 1, TimeUnit.MINUTES); // 챕터 상세 정보를 Redis에 1분 동안 캐싱
    }

    private ChapterDetailDto createChapterDetailDto(Chapter chapter) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedCreatedAt = chapter.getCreatedAt().format(formatter);

        return ChapterDetailDto.builder()
                .chapterId(chapter.getId())
                .bookId(chapter.getBook().getId())
                .title(chapter.getTitle())
                .contents(chapter.getContents())
                .hits(chapter.getHits())
                .createdAt(formattedCreatedAt)
                .build();
    }

}
