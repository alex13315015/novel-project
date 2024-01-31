package com.project.novel.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubscribeDto {

    Integer subscribeCount;
    boolean subscribeState;

}
