package com.project.novel.service;

import com.project.novel.dto.CommentDto;
import com.project.novel.dto.CustomUserDetails;
import com.project.novel.dto.NoticeCommentDto;
import com.project.novel.entity.BoardEntity;
import com.project.novel.entity.CommentEntity;
import com.project.novel.entity.NoticeCommentEntity;
import com.project.novel.entity.NoticeEntity;
import com.project.novel.repository.BoardRepository;
import com.project.novel.repository.CommentRepository;
import com.project.novel.repository.NoticeCommentRepository;
import com.project.novel.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeCommentService {

    private final NoticeCommentRepository noticeCommentRepository;

    private final NoticeRepository noticeRepository;

    @Transactional
    public void saveRefactoring(NoticeCommentDto noticeCommentDto) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        noticeCommentDto.setCommentWriter(userDetails.getLoggedMember().getUserId());

        NoticeEntity noticeEntity = noticeRepository.findById(noticeCommentDto.getNoticeId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        NoticeCommentEntity saveEntity = NoticeCommentEntity.from(noticeCommentDto, noticeEntity);
        noticeCommentRepository.save(saveEntity);
    }

    public List<NoticeCommentDto> findAllRefactoring(Long boardId) {
        NoticeEntity noticeEntity = noticeRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("데이터가 존재하지 않습니다."));

        List<NoticeCommentEntity> commentEntityList = noticeCommentRepository.findAllByNoticeEntityOrderByIdDesc(noticeEntity);

        return NoticeCommentDto.from(commentEntityList, boardId);
    }



    @Transactional
    public Long save(NoticeCommentDto noticeCommentDto) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userDetails.getLoggedMember().getUserId();
        // 부모 엔티티 조회
        Optional<NoticeEntity> optionalNoticeEntity = noticeRepository.findById(noticeCommentDto.getNoticeId());
        if (optionalNoticeEntity.isPresent()){
            NoticeEntity noticeEntity = optionalNoticeEntity.get();
            // CommentDto.toSaveEntity 메서드 업데이트
            NoticeCommentEntity noticeCommentEntity = NoticeCommentEntity.toSaveEntity(noticeCommentDto, noticeEntity);
            return noticeCommentRepository.save(noticeCommentEntity).getId();
        } else {
            return null;
        }
    }


    public List<NoticeCommentDto> findAll(Long boardId) {

        NoticeEntity noticeEntity = noticeRepository.findById(boardId).get();
        List<NoticeCommentEntity> noticeCommentEntityList = noticeCommentRepository.findAllByNoticeEntityOrderByIdDesc(noticeEntity);

        List<NoticeCommentDto> commentDtoList = new ArrayList<>();
        for (NoticeCommentEntity noticeCommentEntity: noticeCommentEntityList){
            NoticeCommentDto noticeCommentDto = NoticeCommentDto.toCommentDto(noticeCommentEntity, boardId);
            commentDtoList.add(noticeCommentDto);
        }
        return commentDtoList;

    }

    @Transactional
    public void delete(Long id) {
        noticeRepository.deleteById(id);
    }
}
