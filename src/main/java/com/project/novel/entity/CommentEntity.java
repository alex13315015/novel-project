package com.project.novel.entity;

import com.project.novel.dto.CommentDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "Board_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentEntity extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter;

    @Column
    private String commentContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    @Builder
    private CommentEntity(Long id, String commentWriter, String commentContent, BoardEntity boardEntity) {
        this.id = id;
        this.commentWriter = commentWriter;
        this.commentContent = commentContent;
        this.boardEntity = boardEntity;
    }

    public static CommentEntity from(CommentDto commentDto, BoardEntity boardEntity) {
        return CommentEntity.builder()
                .commentWriter(commentDto.getCommentWriter())
                .commentContent(commentDto.getCommentContent())
                .boardEntity(boardEntity)
                .build();
    }

    public static CommentEntity toSaveEntity(CommentDto commentDto, BoardEntity boardEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentDto.getCommentWriter());
        commentEntity.setCommentContent(commentDto.getCommentContent());
        commentEntity.setBoardEntity(boardEntity);
        return commentEntity;
    }
}
