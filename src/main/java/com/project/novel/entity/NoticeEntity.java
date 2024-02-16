package com.project.novel.entity;

import com.project.novel.dto.BoardDto;
import com.project.novel.dto.NoticeDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "notice")
public class NoticeEntity extends TimeEntity {

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
    private int fileAttached; // 1 or 0

    @OneToMany(mappedBy = "noticeEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFileEntity> boardFileEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "noticeEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<NoticeCommentEntity> commentEntityList = new ArrayList<>();

    public static NoticeEntity toSaveEntity(NoticeDto noticeDto){
        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setMemberId(noticeDto.getMember_id());
        noticeEntity.setPassword(noticeDto.getPassword());
        noticeEntity.setSubject(noticeDto.getSubject());
        noticeEntity.setContent(noticeDto.getContent());
        noticeEntity.setFileAttached(0);
        return noticeEntity;
    }

    public static NoticeEntity toSaveFileEntity(NoticeDto noticeDto) {
        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setMemberId(noticeDto.getMember_id());
        noticeEntity.setPassword(noticeDto.getPassword());
        noticeEntity.setSubject(noticeDto.getSubject());
        noticeEntity.setContent(noticeDto.getContent());
        noticeEntity.setFileAttached(1);
        return noticeEntity;
    }

    public static NoticeEntity toModifyEntity(NoticeDto noticeDto) {
        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setId(noticeDto.getId());
        noticeEntity.setMemberId(noticeDto.getMember_id());
        noticeEntity.setPassword(noticeDto.getPassword());
        noticeEntity.setSubject(noticeDto.getSubject());
        noticeEntity.setContent(noticeDto.getContent());
        return noticeEntity;
    }


}
