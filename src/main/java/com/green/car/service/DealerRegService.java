package com.green.car.service;

import com.green.car.dto.DealerRegListDto;
import com.green.car.dto.DealerResDto;
import com.green.car.entity.DealerRegister;

import java.util.List;

public interface DealerRegService {
    //등록하기
    void dealerRegSave(DealerResDto dtp);
    //조회하기
    List<DealerRegListDto> getDealerRegList();
    //dto-->entity
    default DealerRegister dtoToEntity(DealerResDto dto){
        DealerRegister dealerRegister = DealerRegister.builder()
                .phone(dto.getPhone())
                .location(dto.getLocation())
                .name(dto.getName())
                .message(dto.getMessage())
                .memberId(dto.getMemberId())
                .build();
        return dealerRegister;
    }
    //entity-dto //
    default  DealerRegListDto entityToDto(DealerRegister dealerRegister){
        DealerRegListDto dto = DealerRegListDto.builder()
                .id(dealerRegister.getId())
                .location(dealerRegister.getLocation())
                .message(dealerRegister.getMessage())
                .name(dealerRegister.getName())
                .phone(dealerRegister.getPhone())
                .memberId(dealerRegister.getMemberId())
                .dealerState(dealerRegister.getDealerState())
                .build();
        return dto;
    }
}
