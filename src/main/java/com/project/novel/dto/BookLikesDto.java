package com.project.novel.dto;

import com.project.novel.entity.Book;
import com.project.novel.entity.BookLikes;
import com.project.novel.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class BookLikesDto {

    int likeCount;
    boolean likeState;

}
