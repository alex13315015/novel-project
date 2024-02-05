package com.project.novel.entity;

import com.project.novel.dto.BoardDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Board")
public class BoardEntity extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String memberId;

    @Column
    private String password;

    @Column(length = 100)
    private String subject;

    @Column(length = 500)
    private String content;

    @Column
    private int hit;

    @Column
    private int fileAttached; // 1 or 0

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFileEntity> boardFileEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();


    public static BoardEntity toSaveEntity(BoardDto boardDto){
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setMemberId(boardDto.getMember_id());
        boardEntity.setPassword(boardDto.getPassword());
        boardEntity.setSubject(boardDto.getSubject());
        boardEntity.setContent(boardDto.getContent());
        boardEntity.setHit(0);
        boardEntity.setFileAttached(0);
        return boardEntity;
    }

    public static BoardEntity toSaveFileEntity(BoardDto boardDto) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setMemberId(boardDto.getMember_id());
        boardEntity.setPassword(boardDto.getPassword());
        boardEntity.setSubject(boardDto.getSubject());
        boardEntity.setContent(boardDto.getContent());
        boardEntity.setHit(0);
        boardEntity.setFileAttached(1);
        return boardEntity;
    }

    public static BoardEntity toModifyEntity(BoardDto boardDto) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDto.getId());
        boardEntity.setMemberId(boardDto.getMember_id());
        boardEntity.setPassword(boardDto.getPassword());
        boardEntity.setSubject(boardDto.getSubject());
        boardEntity.setContent(boardDto.getContent());
        boardEntity.setHit(boardDto.getHit());
        return boardEntity;
    }


}
