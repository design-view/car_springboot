package com.green.car.service;

import com.green.car.constant.Role;
import com.green.car.dto.AdminDealerDto;
import com.green.car.entity.Dealer;
import com.green.car.entity.DealerRegister;
import com.green.car.entity.Member;
import com.green.car.repository.DealerRegisterRepository;
import com.green.car.repository.DealerRepository;
import com.green.car.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DealerServiceImpl implements DealerService{
    private final DealerRepository dealerRepository;
    private final MemberRepository memberRepository;
    private final DealerRegisterRepository dealerRegisterRepository;

    @Override
    public Dealer getDealer(Long dealerId) {
        Dealer dealer = dealerRepository.findById(dealerId).get();
        return dealer;
    }

    @Override
    public void addDealer(AdminDealerDto dto) {

        Member member = memberRepository.findById(dto.getMemberid()).get();
        Dealer dealer1 = dtoToEntity(dto);
        dealer1.setMember(member);
        Dealer dealer = dealerRepository.save(dealer1);
        member.setDealerId(dealer.getId());
        member.setRole(Role.DEALER);
        DealerRegister dealerRegister =dealerRegisterRepository.findByMemberId(dto.getMemberid());
        dealerRegister.setDealerState("승인");
        dealerRegisterRepository.save(dealerRegister);
        memberRepository.save(member);
    }
}
