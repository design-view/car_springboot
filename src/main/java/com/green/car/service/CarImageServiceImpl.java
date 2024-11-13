package com.green.car.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.green.car.dto.CarImageDto;
import com.green.car.entity.CarImage;
import com.green.car.repository.CarImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CarImageServiceImpl implements CarImageService{

    private final AmazonS3Client s3Client;

    @Value("${s3.bucket}")
    private String bucket;

    @Value("${carImgLocation}")
    private String carImageLocation;
    private final CarImageRepository carImageRepository;
    //private final FileService fileService;
    @Override
    public void saveCarImage(CarImage carImg, MultipartFile carImgFile) throws IOException {
        //원본이름
        String oriImgName = carImgFile.getOriginalFilename();

        //파일이 있을때만 업로드
        if(oriImgName != null){
            //s3에 업로드하기
            /* 업로드할 파일의 이름을 변경 */
            String originalFileName = carImgFile.getOriginalFilename();
            String fileName = changeFileName(originalFileName);

            /* S3에 업로드할 파일의 메타데이터 생성 */
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(carImgFile.getContentType());
            metadata.setContentLength(carImgFile.getSize());

            /* S3에 파일 업로드 */
            s3Client.putObject(bucket, fileName, carImgFile.getInputStream(), metadata);

            //파일업로드 uploadFile을 호출하면 경로에 이미지 업로드 되고
            //저장된 파일이름을 리턴함
            //String imgName = fileService.uploadFile(carImageLocation,oriImgName,carImgFile.getBytes());
            //경로+파일명
            String imgUrl = s3Client.getUrl(bucket, fileName).toString();
            //dog.jpg, kdlfjdljfdljfdl.jpg, /images/carimg/dfjdljfdljfdljf.jpg
            carImg.updata(oriImgName,fileName,imgUrl);
            //데이터베이스에 저장
            carImageRepository.save(carImg);
        }

    }
    private String changeFileName(String originalFileName) {
        /* 업로드할 파일의 이름을 변경하는 로직 */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return originalFileName + "_" + LocalDateTime.now().format(formatter);
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
