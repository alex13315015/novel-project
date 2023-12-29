package com.project.novel.entity.book;


import com.project.novel.entity.Member;
import com.project.novel.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "bookName", "member"})
public class Book extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "book_id")
    private Long id;

    private String bookName;

    private String description;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "book")
    private List<Chapter> chapterList = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<Subscribe> subscribeList = new ArrayList<>();

    public Book(String bookName, String description, Member member) {
        this.bookName = bookName;
        this.description = description;
        this.member = member;
    }
}
