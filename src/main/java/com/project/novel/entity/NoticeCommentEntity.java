package com.project.novel.entity;

import com.project.novel.dto.CommentDto;
import com.project.novel.dto.NoticeCommentDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "notice_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeCommentEntity extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter;

    @Column
    private String commentContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private NoticeEntity noticeEntity;

    @Builder
    private NoticeCommentEntity(Long id, String commentWriter, String commentContent, NoticeEntity noticeEntity) {
        this.id = id;
        this.commentWriter = commentWriter;
        this.commentContent = commentContent;
        this.noticeEntity = noticeEntity;
    }

    public static NoticeCommentEntity from(NoticeCommentDto noticeCommentDto, NoticeEntity noticeEntity) {
        return NoticeCommentEntity.builder()
                .commentWriter(noticeCommentDto.getCommentWriter())
                .commentContent(noticeCommentDto.getCommentContent())
                .noticeEntity(noticeEntity)
                .build();
    }

    public static NoticeCommentEntity toSaveEntity(NoticeCommentDto noticeCommentDto, NoticeEntity noticeEntity) {
        NoticeCommentEntity commentEntity = new NoticeCommentEntity();
        commentEntity.setCommentWriter(noticeCommentDto.getCommentWriter());
        commentEntity.setCommentContent(noticeCommentDto.getCommentContent());
        commentEntity.setNoticeEntity(noticeEntity);
        return commentEntity;
    }
}
