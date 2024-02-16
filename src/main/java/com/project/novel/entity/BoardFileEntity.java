package com.project.novel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Board_file")
public class BoardFileEntity extends TimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String copyFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private NoticeEntity noticeEntity;

    public static BoardFileEntity toBoardFileEntity(BoardEntity boardEntity, String originalFileName, String copyFileName){
        BoardFileEntity boardFileEntity = new BoardFileEntity();
        boardFileEntity.setOriginalFileName(originalFileName);
        boardFileEntity.setCopyFileName(copyFileName);
        boardFileEntity.setBoardEntity(boardEntity);
        return boardFileEntity;
    }

    public static BoardFileEntity toNoticefileEntity(NoticeEntity noticeEntity, String originalFileName, String copyFileName){
        BoardFileEntity boardFileEntity = new BoardFileEntity();
        boardFileEntity.setOriginalFileName(originalFileName);
        boardFileEntity.setCopyFileName(copyFileName);
        boardFileEntity.setNoticeEntity(noticeEntity);
        return boardFileEntity;
    }

}
