package com.project.novel.dto;

import com.project.novel.entity.BoardEntity;
import com.project.novel.entity.BoardFileEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BoardDto {

    private Long id;

    private String member_id;

    private String password;

    private String subject;

    private String content;

    private int hit;

    private LocalDateTime createDate;

    private List<MultipartFile> file; // write -> controller에 파일 담는 용도

    private List<String> originalFileName; // 원본 파일

    private List<String> copyFileName; // 서버 저장 파일

    private int fileAttached; // 파일 첨부 여부(첨부 1, 미첨부 0)



    public BoardDto(Long id, String member_id, String subject, int hit, LocalDateTime createDate) {
        this.id = id;
        this.member_id = member_id;
        this.subject = subject;
        this.hit = hit;
        this.createDate = createDate;
    }

    public static BoardDto toBoardDto(BoardEntity boardEntity){
        BoardDto boardDto = new BoardDto();
        boardDto.setId(boardEntity.getId());
        boardDto.setMember_id(boardEntity.getMemberId());
        boardDto.setPassword(boardEntity.getPassword());
        boardDto.setSubject(boardEntity.getSubject());
        boardDto.setContent(boardEntity.getContent());
        boardDto.setHit(boardEntity.getHit());
        boardDto.setCreateDate(boardEntity.getCreate_date());
        if(boardEntity.getFileAttached() == 0){
            boardDto.setFileAttached(boardEntity.getFileAttached());
        } else {
            List<String> originalFileNameList = new ArrayList<>();
            List<String> copyFileNameList = new ArrayList<>();
            boardDto.setFileAttached(boardEntity.getFileAttached());
            for (BoardFileEntity boardFileEntity: boardEntity.getBoardFileEntityList()) {
                originalFileNameList.add(boardFileEntity.getOriginalFileName());
                copyFileNameList.add(boardFileEntity.getCopyFileName());
            }
            boardDto.setOriginalFileName(originalFileNameList);
            boardDto.setCopyFileName(copyFileNameList);
        }
        return boardDto;
    }

    public void setMemberId(String memberId) {
        this.member_id = memberId;
    }
}
