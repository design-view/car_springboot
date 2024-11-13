package com.green.car.controller;

import com.green.car.dto.*;
import com.green.car.service.CarService;
import com.green.car.service.DealerRegService;
import com.green.car.service.DealerService;
import com.green.car.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dealer")
@CrossOrigin(origins = "http://car.jinlabs.net:80")
public class DealerController {
    private final CarService carService;
    //등록
    @PostMapping("/addCar")
    public ResponseEntity addCar(CarDto dto,
                                 @RequestParam("uploadFiles") List<MultipartFile> uploadFiles){
        try {

            System.out.println(dto.toString());
            carService.addCar(dto,uploadFiles);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity("ok",HttpStatus.OK);
    }
    //조회
    @GetMapping("/carList")
    public ResponseEntity listCar(@RequestParam("dealerId") Long dealerId){
        List<ListCarDto> result= carService.getDealerCarList(dealerId);
        return new ResponseEntity(result,HttpStatus.OK);
    }
    //수정
    @PostMapping("/carEdit")
    public ResponseEntity editCar(CarDto cardto){
        System.out.println(cardto.toString());
        try {
            carService.editCar(cardto);
        }
        catch (Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  new ResponseEntity("ok",HttpStatus.OK);
    }
}
