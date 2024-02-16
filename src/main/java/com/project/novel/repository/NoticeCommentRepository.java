package com.project.novel.repository;

import com.project.novel.entity.BoardEntity;
import com.project.novel.entity.CommentEntity;
import com.project.novel.entity.NoticeCommentEntity;
import com.project.novel.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeCommentRepository extends JpaRepository<NoticeCommentEntity, Long> {
    List<NoticeCommentEntity> findAllByNoticeEntityOrderByIdDesc(NoticeEntity noticeEntity);
}
