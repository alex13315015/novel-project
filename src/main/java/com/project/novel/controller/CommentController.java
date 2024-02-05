package com.project.novel.controller;

import com.project.novel.dto.CommentDto;
import com.project.novel.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/write")
    public ResponseEntity write(@ModelAttribute CommentDto commentDto){

        Long saveResult = commentService.save(commentDto);
        if (saveResult != null){
            List<CommentDto> commentDtoList = commentService.findAll(commentDto.getBoardId());
            return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 댓글이 존재하지 않습니다. 다시 확인해주세요.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/write1")
    public ResponseEntity write1(@ModelAttribute CommentDto commentDto){
        commentService.saveRefactoring(commentDto);

        return ResponseEntity.ok(commentService.findAllRefactoring(commentDto.getBoardId()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam(value = "commentId", required = true) Long commentId,
                                 @RequestParam(value = "boardId", required = true) Long boardId){
        commentService.delete(commentId);

        return new ResponseEntity<>(commentService.findAllRefactoring(boardId), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity list(@RequestParam(value = "boardId", required = true) Long boardId){
        List<CommentDto> commentDtoList = commentService.findAllRefactoring(boardId);

        return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
    }

}
