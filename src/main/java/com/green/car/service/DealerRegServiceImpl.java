package com.green.car.service;

import com.green.car.dto.DealerRegListDto;
import com.green.car.dto.DealerResDto;
import com.green.car.entity.Dealer;
import com.green.car.entity.DealerRegister;
import com.green.car.repository.DealerRegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DealerRegServiceImpl implements DealerRegService{

    private final DealerRegisterRepository dealerRegisterRepository;

    //딜러신청 게시글 등록 (dto--> entity변환 save)
    @Override
    public void dealerRegSave(DealerResDto dto) {
        DealerRegister dealerRegister = dtoToEntity(dto);
        dealerRegister.setDealerState("신청");
        dealerRegisterRepository.save(dealerRegister);
    }
    //딜러목록조회하기
    @Override
    public List<DealerRegListDto> getDealerRegList() {
        List<DealerRegister>  result = dealerRegisterRepository.findAll();
        List<DealerRegListDto> dtos =
        result.stream().map(dr->entityToDto(dr)).collect(Collectors.toList());
        return dtos;
    }
}
