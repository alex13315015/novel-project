package com.project.novel.service;

import com.project.novel.dto.CommentDto;
import com.project.novel.dto.CustomUserDetails;
import com.project.novel.entity.BoardEntity;
import com.project.novel.entity.CommentEntity;
import com.project.novel.repository.BoardRepository;
import com.project.novel.repository.CommentRepository;
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
public class CommentService {

    private final CommentRepository commentRepository;

    private final BoardRepository boardRepository;

    @Transactional
    public void saveRefactoring(CommentDto commentDto) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        commentDto.setCommentWriter(userDetails.getLoggedMember().getUserId());

        BoardEntity boardEntity = boardRepository.findById(commentDto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        CommentEntity saveEntity = CommentEntity.from(commentDto, boardEntity);
        commentRepository.save(saveEntity);
    }

    public List<CommentDto> findAllRefactoring(Long boardId) {
        BoardEntity boardEntity = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("데이터가 존재하지 않습니다."));

        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);

        return CommentDto.from(commentEntityList, boardId);
    }



    @Transactional
    public Long save(CommentDto commentDto) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userDetails.getLoggedMember().getUserId();
        // 부모 엔티티 조회
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDto.getBoardId());
        if (optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
            // CommentDto.toSaveEntity 메서드 업데이트
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDto, boardEntity);
            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }
    }


    public List<CommentDto> findAll(Long boardId) {
        //select * from board_comment where board_id=? order by id desc;
        BoardEntity boardEntity = boardRepository.findById(boardId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);
        //EntityList -> DtoList 변환
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (CommentEntity commentEntity: commentEntityList){
            CommentDto commentDto = CommentDto.toCommentDto(commentEntity, boardId);
            commentDtoList.add(commentDto);
        }
        return commentDtoList;

    }


    public List<CommentEntity> getAllComments() {
        // 댓글 목록을 가져오는 로직
        return commentRepository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
