package com.kushPmakwana.mangia.Mangia.dto.response;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class ListResponse<T> {
    private long total;
    private int pageNo;
    private int pageSize;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrev;
    private List<T> data;

    public ListResponse(Page<?> pageObj, List<T> data){
        this.total = pageObj.getTotalElements();
        this.pageNo = pageObj.getNumber()+1;
        this.pageSize = pageObj.getSize();
        this.totalPages = pageObj.getTotalPages();
        this.hasNext = pageObj.hasNext();
        this.hasPrev = pageObj.hasPrevious();
        this.data = data;
    }

}
