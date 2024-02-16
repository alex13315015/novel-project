package com.project.novel.dto;

import com.project.novel.entity.BoardEntity;
import com.project.novel.entity.BoardFileEntity;
import com.project.novel.entity.NoticeEntity;
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
public class NoticeDto {

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



    public NoticeDto(Long id, String member_id, String subject, int hit, LocalDateTime createDate) {
        this.id = id;
        this.member_id = member_id;
        this.subject = subject;
        this.hit = hit;
        this.createDate = createDate;
    }

    public static NoticeDto toNoticeDto(NoticeEntity noticeEntity){
        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setId(noticeEntity.getId());
        noticeDto.setMember_id(noticeEntity.getMemberId());
        noticeDto.setPassword(noticeEntity.getPassword());
        noticeDto.setSubject(noticeEntity.getSubject());
        noticeDto.setContent(noticeEntity.getContent());
        noticeDto.setCreateDate(noticeEntity.getCreate_date());
        if(noticeEntity.getFileAttached() == 0){
            noticeDto.setFileAttached(noticeEntity.getFileAttached());
        } else {
            List<String> originalFileNameList = new ArrayList<>();
            List<String> copyFileNameList = new ArrayList<>();
            noticeDto.setFileAttached(noticeEntity.getFileAttached());
            for (BoardFileEntity boardFileEntity: noticeEntity.getBoardFileEntityList()) {
                originalFileNameList.add(boardFileEntity.getOriginalFileName());
                copyFileNameList.add(boardFileEntity.getCopyFileName());
            }
            noticeDto.setOriginalFileName(originalFileNameList);
            noticeDto.setCopyFileName(copyFileNameList);
        }
        return noticeDto;
    }

    public void setMemberId(String memberId) {
        this.member_id = memberId;
    }
}
