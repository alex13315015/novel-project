package com.project.novel.dto;

import com.project.novel.entity.CommentEntity;
import com.project.novel.entity.NoticeCommentEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeCommentDto {

    private Long id;

    private String commentWriter;

    private String commentContent;

    private Long noticeId;

    private LocalDateTime commentCreateDate;

    private boolean canDelete;

    public static NoticeCommentDto toCommentDto(NoticeCommentEntity commentEntity, Long boardId) {
        NoticeCommentDto commentDto = new NoticeCommentDto();
        commentDto.setId(commentEntity.getId());
        commentDto.setCommentWriter(commentEntity.getCommentWriter());
        commentDto.setCommentContent(commentEntity.getCommentContent());
        commentDto.setCommentCreateDate(commentEntity.getCreate_date());
        //commentDto.setBoardId(commentEntity.getBoardEntity().getId());
        commentDto.setNoticeId(boardId);
        return commentDto;
    }

    public static List<NoticeCommentDto> from(List<NoticeCommentEntity> commentEntityList, Long boardId) {
        return commentEntityList.stream()
                .map(commentEntity -> toCommentDto(commentEntity, boardId))
                .toList();
    }
}
