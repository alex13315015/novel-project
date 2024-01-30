package com.project.novel.repository;

import com.project.novel.entity.Views;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ViewRepository extends JpaRepository<Views, Long> {

    @Query("SELECT v FROM Views v WHERE v.member.id = :memberId AND v.chapter.id = :chapterId")
    Views findByMemberIdAndChapterId(@Param("memberId") Long memberId, @Param("chapterId") Long chapterId);


    List<Views> findAllByMemberId(Long loggedId);
}
