package com.project.novel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class) // 자동으로 날짜를 생성
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;

    private String userName;

    private String nickname;

    private String password;

    @Email
    private String email;

    @Column(length = 2)
    private Integer age;

    private String phoneNumber;

    private Integer coin;

    private String role;
    // 등급에 따른 role 부여하는 컬럼

    private String profileImage;

    @CreatedDate
    private LocalDateTime createdAt;
    // 회원 등록 날짜
    @LastModifiedDate
    private LocalDateTime updatedAt;
    // 회원정보수정 날짜
}
