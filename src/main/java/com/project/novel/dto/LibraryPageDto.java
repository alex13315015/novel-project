package com.project.novel.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter @Setter
public class LibraryPageDto {
    private long totalRecordCount;
    private long totalPageCount;
    private long page;

    public LibraryPageDto(Page<Object[]> page){
        this.totalRecordCount = page.getTotalElements();
        this.totalPageCount = page.getTotalPages();
        this.page = page.getNumber() + 1;
        if(this.page > totalPageCount){
            this.page = totalPageCount;
        }
    }

    public long getStartPage(){
        return  ((page - 1) / 5) * 5 + 1;
    }

    public long getEndPage(){
        long endPage = getStartPage() + 5 - 1;
        if(endPage > totalPageCount){
            endPage = totalPageCount;
        }
        return endPage;
    }

    public boolean existPrevPage(){
        return page > 1;
    }

    public boolean existNextPage(){
        return page < totalPageCount;
    }


}
