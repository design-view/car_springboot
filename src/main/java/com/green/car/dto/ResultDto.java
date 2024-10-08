package com.green.car.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class ResultDto<DTO,EN> {
    //dto리스트
    private List<DTO> dtoList;
    //총페이지번호
    private int totalPage;
    //현재페이지번호
    private int page;
    //목록사이즈
    private int size;
    //시작페이지번호,끝페이지번호
    private int start, end;
    //이전,다음
    private boolean prev,next;
    //페이지 번호 목록
    private List<Integer> pageList;
    //생성자
    public ResultDto(Page<EN> result, Function<EN,DTO> fn){
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }
    //페이지 만들기
    private void makePageList(Pageable pageable){
        this.page = pageable.getPageNumber()+1;
        this.size = pageable.getPageSize();
        int tempEnd = (int) (Math.ceil(page/10.0))*10;
        start =  tempEnd-9;
        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd : totalPage;
        next = totalPage > tempEnd;
        pageList = IntStream.rangeClosed(start,end)
                .boxed().collect(Collectors.toList());;
    }
}
