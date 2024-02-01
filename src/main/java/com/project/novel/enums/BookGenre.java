package com.project.novel.enums;

import lombok.Getter;

@Getter
public enum BookGenre {
    FANTASY("판타지"),
    SCIENCE_FICTION("과학소설"),
    MYSTERY("미스터리"),
    THRILLER("스릴러"),
    ROMANCE("로맨스"),
    HORROR("공포"),
    ORIENTAL_FANTASY("무협");

    private final String displayName;

    BookGenre(String displayName) {
        this.displayName = displayName;
    }

}
