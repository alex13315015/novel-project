package com.project.novel.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum Genre {

    NOVEL("소설"),
    FANTASY("판타지"),
    ROMANCE("로맨스"),
    MYSTERY("추리"),
    ESSAY("에세이"),
    POETRY("시"),
    EDUCATION("교육"),
    OTHER("기타");

    private final String genreName;

    Genre(String genreName){
        this.genreName = genreName;
    }
}
