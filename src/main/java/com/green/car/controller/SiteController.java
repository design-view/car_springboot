package com.green.car.controller;

import com.green.car.dto.DealerResDto;
import com.green.car.dto.MemberJoinDto;
import com.green.car.dto.MemberLoginDto;
import com.green.car.dto.TokenDto;
import com.green.car.service.DealerRegService;
import com.green.car.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/site")
@CrossOrigin(origins = "http://3.36.54.190:80")
public class SiteController {
    private final MemberService memberService;
    
    //로그인 요청
    @PostMapping("/login")
    public TokenDto login(@RequestBody MemberLoginDto memberLoginDto){
        TokenDto token = memberService.login(memberLoginDto.getUsername()
                ,memberLoginDto.getPassword());
        return token;
    }
    
    //회원가입요청
    @PostMapping("/join")
    public String memberJoin(@RequestBody MemberJoinDto memberJoinDto){
        try{
            memberService.saveMember(memberJoinDto);
            return "ok";
        }
        catch (Exception e){
            return "fail";
        }
    }


}
