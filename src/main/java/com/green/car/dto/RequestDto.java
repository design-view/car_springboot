package com.green.car.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@Data
@AllArgsConstructor
public class RequestDto {
    private int page;
    private int size;
    private Long categoryId;

    public RequestDto(){
        this.page = 1;
        this.size = 4;
        this.categoryId = 1L;
    }
    public Pageable getPageable(Sort sort){
        return PageRequest.of(page-1,size,sort);
    }
}
