package com.green.car.controller;

import com.green.car.dto.*;
import com.green.car.entity.Car;
import com.green.car.entity.Category;
import com.green.car.repository.CarRepository;
import com.green.car.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/car")
@CrossOrigin(origins = "http://localhost:3000")
public class CarController {
    private final CarService carService;
    @GetMapping("/category")
    public ResponseEntity getCategory(@RequestParam("category") Long categoryId,
                                      @RequestParam("maker") Long makerId){
        System.out.println("카테고리아이디는" +categoryId);
        System.out.println("메이커아이디는" +makerId);
        CategoryDto result = carService.getCategory(categoryId,makerId);
        return new ResponseEntity<>(result ,HttpStatus.OK);
    }

    //메인카리스트 조회  요청시 RequestDto타입을 받고  { categoryId : 2}
    //응답시 ResultDto타입으로 응답
    @RequestMapping("/cars")
    public ResponseEntity getCars(RequestDto requestDto){
        //자동차를 검색해서 반환
        ResultDto<MainCarDto,Object[]> result = carService.getMainList(requestDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    //중고차목록페이지
    @GetMapping("/carlist")
    public ResponseEntity getCarList(@RequestParam("categoryId") Long id,
                                     @RequestParam("maker") String maker,
                                     @RequestParam("model") String model){
        System.out.println("메이커 값은 : " + maker);
        System.out.println("모델 값은 : " + model);
        List<ListCarDto> result = carService.getPageList(id,maker,model);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/image")
    public ResponseEntity<?> returnImage(@RequestParam("image") String image) {
        String path = "C:\\car\\image\\"; //이미지가 저장된 위치
        Resource resource = new FileSystemResource(path + image);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
    @GetMapping("/car/{carId}")
    public ResponseEntity<CarDto> carDetail(@PathVariable("carId") Long carId){
        CarDto carDto = carService.findById(carId);
        return new ResponseEntity<>(carDto,HttpStatus.OK);
    }
}
