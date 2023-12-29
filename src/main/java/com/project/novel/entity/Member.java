package com.project.novel.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class) // 자동으로 날짜를 생성
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId")
    private int id;

    private String userId;
    private String userName;
    private String nickName;
    private String password;
    private String email;
    private Integer age;
    private String phoneNumber;
    private Integer coin;

    private boolean adminCheck;
    // 일반 사용자와 관리자를 구분하는 컬럼
    @CreatedDate
    private LocalDateTime createdAt;
    // 회원 등록 날짜
    @LastModifiedDate
    private LocalDateTime updatedAt;
    // 회원정보수정 날짜
}
