package com.project.novel.api;

import com.project.novel.dto.CustomUserDetails;
import com.project.novel.dto.SubscribeDto;
import com.project.novel.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SubscribeApiController {

    private final SubscribeService subscribeService;

    @PostMapping("/book/{bookId}/subscribe")
    public SubscribeDto subscribeBook(@PathVariable(name="bookId") Long bookId,
                                      @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        subscribeService.subscribe(bookId, customUserDetails.getLoggedMember().getId());
        return SubscribeDto.builder()
                .subscribeCount(subscribeService.subscribeCount(bookId))
                .subscribeState(subscribeService.isSubscribed(bookId, customUserDetails.getLoggedMember().getId()))
                .build();
    }

    @DeleteMapping("/book/{bookId}/subscribe")
    public SubscribeDto unsubscribeBook(@PathVariable(name="bookId") Long bookId,
                                        @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        subscribeService.unsubscribe(bookId, customUserDetails.getLoggedMember().getId());
        return SubscribeDto.builder()
                .subscribeCount(subscribeService.subscribeCount(bookId))
                .subscribeState(subscribeService.isSubscribed(bookId, customUserDetails.getLoggedMember().getId()))
                .build();
    }
}
