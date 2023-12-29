package com.project.novel.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"id"})
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private String password;
    private String email;
    private Integer age;
    private String phone;
    private Integer coin;
    private boolean adminCheck;

    public Member(String username, String password, String email, Integer age, String phone, Integer coin, boolean adminCheck) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
        this.phone = phone;
        this.coin = coin;
        this.adminCheck = adminCheck;
    }
}
