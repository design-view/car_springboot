package com.green.car.service;

import com.green.car.dto.CarImageDto;
import com.green.car.entity.CarImage;
import com.green.car.repository.CarImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CarImageServiceImpl implements CarImageService{

    @Value("${carImgLocation}")
    private String carImageLocation;
    private final CarImageRepository carImageRepository;
    private final FileService fileService;
    @Override
    public void saveCarImage(CarImage carImg, MultipartFile carImgFile) throws IOException {
        //원본이름
        String oriImgName = carImgFile.getOriginalFilename();
        //파일이 있을때만 업로드
        if(oriImgName != null){
            //파일업로드 uploadFile을 호출하면 경로에 이미지 업로드 되고
            //저장된 파일이름을 리턴함
            String imgName = fileService.uploadFile(carImageLocation,oriImgName,carImgFile.getBytes());
            //경로+파일명
            String imgUrl = "/images/carimg/"+imgName;
            //dog.jpg, kdlfjdljfdljfdl.jpg, /images/carimg/dfjdljfdljfdljf.jpg
            carImg.updata(oriImgName,imgName,imgUrl);
            //데이터베이스에 저장
            carImageRepository.save(carImg);
        }

    }

    @Override
    public List<CarImageDto> findByCarId(Long carId) {
        List<CarImage> result = carImageRepository.findByCarId(carId);
        //앤티티가 담긴 리스트를 dto가담긴 리스트로 변환
        List<CarImageDto> carImageDtos = result.stream()
                .map(entity->entityToDto(entity)).collect(Collectors.toList());
        return carImageDtos;
    }
}
