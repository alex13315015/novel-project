package com.project.novel.entity;

import com.project.novel.enums.MyRole;
import jakarta.persistence.*;
import lombok.*;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String loginId;
    private String name;
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private MyRole role;

}
