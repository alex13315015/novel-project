package com.project.novel.entity;

import com.project.novel.constant.Grade;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.validation.annotation.Validated;

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

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String userName;

    private String nickName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column(length = 2)
    private Integer age;

    private String phoneNumber;

    private Integer coin;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Grade role;
    // 등급에 따른 role 부여하는 컬럼

    @CreatedDate
    private LocalDateTime createdAt;
    // 회원 등록 날짜
    @LastModifiedDate
    private LocalDateTime updatedAt;
    // 회원정보수정 날짜
}
