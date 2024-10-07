package com.green.car.controller;

import com.green.car.dto.*;
import com.green.car.service.CarService;
import com.green.car.service.DealerRegService;
import com.green.car.service.DealerService;
import com.green.car.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
    private final CarService carService;
    private final MemberService memberService;
    private final DealerRegService dealerRegService;
    private final DealerService dealerService;
    //회원목록
    @GetMapping("/memberList")
    public ResponseEntity getMemberList(){
        List<MemberDto> result = memberService.getMemberList();
        return new ResponseEntity(result,HttpStatus.OK);
    }
    //딜러요청목록
    @GetMapping("/dealerRegList")
    public ResponseEntity getDealerRegList(){
        List<DealerRegListDto> result = dealerRegService.getDealerRegList();
        return new ResponseEntity(result,HttpStatus.OK);
    }
    //딜러등록
    @PostMapping("/dealerAdd")
    public ResponseEntity dealerAdd(@RequestBody AdminDealerDto dto){
        System.out.println(dto.toString());
        dealerService.addDealer(dto);
        return new ResponseEntity("OK",HttpStatus.OK);
    }
    //딜러목록
    //자동차목록
    //카테고리추가삭제
}
