package com.project.novel.api;

import com.project.novel.dto.CustomUserDetails;
import com.project.novel.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class SubscribeApiController {
    private final SubscribeService subscribeService;

    @PostMapping("/subscribe/{book_id}")
    public Map<String,Object> subscribe(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                        @PathVariable Long book_id) {
        subscribeService.subscribe(customUserDetails.getLoggedMember().getId(), book_id);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("isSubscribe","OK");
        return resultMap;
    }
    @DeleteMapping("/subscribe/{book_id}")
    public Map<String,Object> subscribeDelete(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                              @PathVariable Long book_id) {
        subscribeService.unSubscribe(customUserDetails.getLoggedMember().getId(), book_id);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("notSubscribe","OK");
        return resultMap;
    }
}
