package com.project.novel.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberInfoDto {

    private List<BookListDto> bookList;
}
