package com.green.car.service;

import com.green.car.dto.CarImageDto;
import com.green.car.entity.CarImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CarImageService {
    //carimg엔티티 저장
    void saveCarImage(CarImage carImg, MultipartFile carImgFile) throws IOException;
    //carId에 일치하는 img들 조회
    List<CarImageDto> findByCarId(Long carId);

    default CarImageDto entityToDto(CarImage carImage){
        CarImageDto carImageDto = CarImageDto.builder()
                .id(carImage.getId())
                .imgName(carImage.getImgName())
                .imgUrl(carImage.getImgUrl())
                .oriImgName(carImage.getOriImgName())
                .repimgYn(carImage.getRepimgYn())
                .build();
        return carImageDto;
    }
}
