package com.project.novel.service;

import com.project.novel.dto.NoticeDto;
import com.project.novel.entity.BoardFileEntity;
import com.project.novel.entity.NoticeEntity;
import com.project.novel.repository.BoardFileRepository;
import com.project.novel.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    public final NoticeRepository noticeRepository;

    public final BoardFileRepository boardFileRepository;


    public void save(NoticeDto noticeDto) throws IOException {
        // 파일 첨부 여부에 따른 로직 분리
        if (noticeDto.getFile().isEmpty()) {
            NoticeEntity noticeEntity = NoticeEntity.toSaveEntity(noticeDto);
            noticeRepository.save(noticeEntity);
        } else {
            NoticeEntity noticeEntity = NoticeEntity.toSaveFileEntity(noticeDto);
            Long saveId = noticeRepository.save(noticeEntity).getId();
            NoticeEntity notice = noticeRepository.findById(saveId).orElseThrow(() -> new RuntimeException("Board not found"));

            for (MultipartFile boardFile : noticeDto.getFile()) {
                if (!boardFile.isEmpty()) {
                    String originalFileName = boardFile.getOriginalFilename();
                    String copyFileName = System.currentTimeMillis() + "_" + originalFileName;
                    String savePath = "C:/novel_img/" + copyFileName;
                    boardFile.transferTo(new File(savePath));

                    // 리사이징 추가
                    int resizedWidth = 300;
                    int resizedHeight = 300;

                    // 리사이징된 이미지를 새로운 경로에 저장
                    String resizedSavePath = "C:/novel_img/resized_" + copyFileName;

                    // 이미지 리사이징
                    Thumbnails.of(savePath)
                            .size(resizedWidth, resizedHeight)
                            .toFile(resizedSavePath);

                    BoardFileEntity boardFileEntity = BoardFileEntity.toNoticefileEntity(notice, originalFileName, "resized_" + copyFileName);
                    boardFileRepository.save(boardFileEntity);
                }
            }
        }
    }


    @Transactional
    public List<NoticeDto> findAll() {
        List<NoticeEntity> noticeEntityList = noticeRepository.findAll();
        List<NoticeDto> noticeDtoList = new ArrayList<>();
        for(NoticeEntity noticeEntity: noticeEntityList) {
            noticeDtoList.add(NoticeDto.toNoticeDto(noticeEntity));
        }
        return noticeDtoList;
    }

    @Transactional
    public NoticeDto findById(Long id) {
        Optional<NoticeEntity> optionalNoticeEntity = noticeRepository.findById(id);
        if (optionalNoticeEntity.isPresent()){
            NoticeEntity noticeEntity = optionalNoticeEntity.get();
            NoticeDto noticeDto = NoticeDto.toNoticeDto(noticeEntity);
            return noticeDto;
        }else {
            return null;
        }
    }

    @Transactional
    public NoticeDto modify(NoticeDto noticeDto) {
        NoticeEntity noticeEntity = NoticeEntity.toModifyEntity(noticeDto);
        noticeRepository.save(noticeEntity);
        return findById(noticeDto.getId());
    }


    // 보통은 이런 경우 QueryDSL을 사용
    public Page<NoticeDto> searchAndPaging(String category, String keyword, Pageable pageable) {
        if (category != null && !category.isEmpty() && keyword != null && !keyword.isEmpty()) {

            if ("title".equals(category)) {
                return noticeRepository.findBySubjectContaining(keyword, pageable).map(NoticeDto::toNoticeDto);
            } else if ("content".equals(category)) {
                return noticeRepository.findByContentContaining(keyword, pageable).map(NoticeDto::toNoticeDto);
            } else if ("writer".equals(category)) {
                return noticeRepository.findByMemberIdContaining(keyword, pageable).map(NoticeDto::toNoticeDto);
            }
        }
        return noticeRepository.findAll(pageable).map(NoticeDto::toNoticeDto);
    }
}
