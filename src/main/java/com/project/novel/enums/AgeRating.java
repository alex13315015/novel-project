package com.project.novel.enums;

import lombok.Getter;

@Getter
public enum AgeRating {

    ALL(0, "전체 이용가"),
    AGE_19(19, "청소년 관람 불가");

    private final int age;
    private final String ratingName;

    AgeRating(int age, String ratingName){
        this.age = age;
        this.ratingName = ratingName;
    }
}
