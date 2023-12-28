package com.project.novel.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
    private String createdAt;
    private String updatedAt;
}
