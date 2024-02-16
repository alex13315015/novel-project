package com.project.novel.controller;

import com.project.novel.dto.CommentDto;
import com.project.novel.dto.NoticeCommentDto;
import com.project.novel.service.NoticeCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/notice/comment")
public class NoticeCommentController {

    private final NoticeCommentService noticeCommentService;


    @PostMapping("/write")
    public ResponseEntity write(@ModelAttribute NoticeCommentDto noticeCommentDto){
        noticeCommentService.saveRefactoring(noticeCommentDto);
        List<NoticeCommentDto> result = noticeCommentService.findAllRefactoring(noticeCommentDto.getNoticeId());
        isCanDelete(result);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam(value = "commentId", required = true) Long commentId,
                                 @RequestParam(value = "noticeId", required = true) Long noticeId){
        noticeCommentService.delete(commentId);
        List<NoticeCommentDto> result = noticeCommentService.findAllRefactoring(noticeId);
        isCanDelete(result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity list(@RequestParam(value = "noticeId", required = true) Long noticeId){
        List<NoticeCommentDto> noticeCommentDtoList = noticeCommentService.findAllRefactoring(noticeId);
        // 현재 사용자의 권한 및 아이디 확인 후 canDelete 설정
        isCanDelete(noticeCommentDtoList);

        return new ResponseEntity<>(noticeCommentDtoList, HttpStatus.OK);
    }


    private static void isCanDelete(List<NoticeCommentDto> noticeCommentDtoList) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        noticeCommentDtoList.forEach(dto -> {
            boolean isOwner = dto.getCommentWriter().equals(authentication.getName());
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
            dto.setCanDelete(isOwner || isAdmin);
        });
    }

}
