package com.green.car.service;

import com.green.car.dto.AdminDealerDto;
import com.green.car.entity.Dealer;

public interface DealerService {

    //딜러조회
    Dealer getDealer(Long dealerId);

    //딜러등록
    void addDealer(AdminDealerDto dto);

    default Dealer dtoToEntity(AdminDealerDto dto){
        Dealer dealer = Dealer.builder()
                .location(dto.getLocation())
                .name(dto.getName())
                .phone(dto.getPhone())
                .build();
        return dealer;
    }
}
