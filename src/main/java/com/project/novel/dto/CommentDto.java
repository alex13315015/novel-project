package com.project.novel.dto;

import com.project.novel.entity.CommentEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {

    private Long id;

    private String commentWriter;

    private String commentContent;

    private Long boardId;

    private LocalDateTime commentCreateDate;

    public static CommentDto toCommentDto(CommentEntity commentEntity, Long boardId) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(commentEntity.getId());
        commentDto.setCommentWriter(commentEntity.getCommentWriter());
        commentDto.setCommentContent(commentEntity.getCommentContent());
        commentDto.setCommentCreateDate(commentEntity.getCreate_date());
        //commentDto.setBoardId(commentEntity.getBoardEntity().getId());
        commentDto.setBoardId(boardId);
        return commentDto;
    }

    public static List<CommentDto> from(List<CommentEntity> commentEntityList, Long boardId) {
        return commentEntityList.stream()
                .map(commentEntity -> toCommentDto(commentEntity, boardId))
                .toList();
    }
}
