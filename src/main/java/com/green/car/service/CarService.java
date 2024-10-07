package com.green.car.service;

import com.green.car.dto.*;
import com.green.car.entity.Car;
import com.green.car.entity.CarImage;
import com.green.car.entity.Category;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CarService {
    //addCar호출시 매개변수 dto, List<MultipartFile>
    void addCar(CarDto dto, List<MultipartFile> carImgFileList) throws IOException;
    Long carRemove(Long id);
    CarDto findById(Long id);
    //car목록 조회하기
    List<Car> carlist();
    //메인car목록 조회하기
    ResultDto<MainCarDto,Object[]> getMainList(RequestDto dto);
    CategoryDto getCategory(Long categoryId, Long makerId);
    default Car dtoToEntity(CarDto dto){
        Car car = Car.builder()
                .title(dto.getTitle())
                .price(dto.getPrice())
                .cardesc(dto.getCardesc())
                .displacement(dto.getDisplacement())
                .mileage(dto.getMileage())
                .transmission(dto.getTransmission())
                .fuel(dto.getFuel())
                .year(dto.getYear())
                .color(dto.getColor())
                .registerNumber(dto.getRegisterNumber())
                .build();
        return car;
    }
    List<MainCarDto> getCarList();

    //차목록페이지 조회하기
    List<ListCarDto> getPageList(Long id, String maker, String model);
    //엔티티 오브젝트를 MainCarDto로 변환하기
    default MainCarDto entityObjtoDto(Car car, CarImage carImage){
        MainCarDto mainCarDto = MainCarDto.builder()
                .id(car.getId())
                .title(car.getTitle())
                .price(car.getPrice())
                .makerName(car.getMaker().getMakerName())
                .imgName(carImage.getImgName())
                .build();
        return mainCarDto;
    }
    //딜러id로 조회하기
    List<ListCarDto> getDealerCarList(Long dealerId);

    //등록자동차 정보수정하기
    void editCar(CarDto dto);
}
