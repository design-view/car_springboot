package com.green.car.controller;

import com.green.car.dto.DealerResDto;
import com.green.car.dto.MemberJoinDto;
import com.green.car.dto.MemberLoginDto;
import com.green.car.dto.TokenDto;
import com.green.car.service.DealerRegService;
import com.green.car.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@CrossOrigin(origins = "http://3.36.54.190")
public class MemberController {
    private final MemberService memberService;
    private final DealerRegService dealerRegService;

    //딜러등록하기
    @PostMapping("/register")
    public String memberJoin(@RequestBody DealerResDto dto){
        try{
            dealerRegService.dealerRegSave(dto);
            return "ok";
        }
        catch (Exception e){
            return "fail";
        }
    }
    @PostMapping("/test")
    public String test(){
        return "success";
    }
    @PostMapping("/usertest")
    public String usertest(){
        //SecurityContextHolder->SecurityContext->Authentication
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        if(authentication == null || authentication.getName() == null){
            throw new RuntimeException("인증 정보가 없습니다.");
        }
        return authentication.getName();
    }

}
