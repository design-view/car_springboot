package com.green.car.service;

import com.green.car.dto.*;
import com.green.car.entity.*;
import com.green.car.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService{
    private final CarRepository carRepository;
    private final DealerRepository dealerRepository;
    private final CarImageService carImageService;
    private final DealerService dealerService;
    private final CategoryRepository categoryRepository;
    private final MakerRepository makerRepository;
    private final ModelRepository modelRepository;

    @Override
    public void addCar(CarDto dto, List<MultipartFile> carImgFileList) throws IOException {
        //입력받은 값으로 car객체 대입
        Car car = dtoToEntity(dto);
        //엔티티 타입의 필드는 입력받은 값으로 조회후 setter로 대입
        Dealer dealer = dealerRepository.findById(dto.getDealerId()).get();
        //카테고리,메이커,모델
        Category category =  categoryRepository.findById(dto.getCategoryId()).get();
        Maker maker = makerRepository.findById(dto.getMakerId()).get();
        Model model = modelRepository.findById(dto.getModelId()).get();
        car.setDealer(dealer);
        car.setCategory(category);
        car.setMaker(maker);
        car.setModel(model);
        carRepository.save(car);
        System.out.println("==================================");
        System.out.println(car);
        System.out.println("==================================");
        //이미지등록
        for(int i=0; i<carImgFileList.size(); i++){
            CarImage carImage = new CarImage();
            //참조타입 필드 지정
            carImage.setCar(car);
            if(i==0){
                //첫번째 이미지 파일일때만 Y값 지정
                carImage.setRepimgYn("Y");
            }else {
                //나머지 이미지 파일은 N값 지정
                carImage.setRepimgYn("N");
            }
            carImageService.saveCarImage(carImage,carImgFileList.get(i));
        }
    }
    @Override
    public Long carRemove(Long id) {
        return null;
    }

    @Override
    public CarDto findById(Long id) {
        //1.car조회
        Car car = carRepository.findById(id).get();
        CarDto dto = new CarDto();
        //dto 카관련 필드값 업데이트
        dto.update(car.getCategory().getId(),
                car.getMaker().getId(),
                car.getModel().getId(),
                car.getColor(),
                car.getRegisterNumber(),
                car.getYear(),
                car.getPrice(),
                car.getId(),
                car.getDisplacement(),
                car.getMileage(),
                car.getTransmission(),
                car.getFuel(),
                car.getTitle(),
                car.getCardesc());
        //2.carid에 맞는 CarImage
        List<CarImageDto> result = carImageService.findByCarId(id);
        dto.setCarImageDtos(result);
        //3.dealer정보 할당
        dto.setDealer(car.getDealer());
        return dto;
    }

    @Override
    public List<Car> carlist() {
        List<Car> cars = carRepository.findAll();
        return cars;
    }

    @Override
    //메인 목록 조회 (베스트 수입차, 베스트 국산차)
    public ResultDto<MainCarDto,Object[]> getMainList(RequestDto dto) {
        //pageable객체 생성
        Pageable pageable = dto.getPageable(Sort.by("id").descending());
        Page<Object[]> result=
                carRepository.getCarMainList(pageable,dto.getCategoryId());
        Function<Object[], MainCarDto> fn = (arr->{
            return entityObjtoDto((Car) arr[0], (CarImage) arr[1]);
        });
        return new ResultDto<>(result,fn);
    }

    @Override
    public List<MainCarDto> getCarList() {
        List<Object[]> result = carRepository.getCarList();
        List<MainCarDto> carDtoList = new ArrayList<>();
        for(Object[] obj: result){
            MainCarDto dto = entityObjtoDto((Car) obj[0],(CarImage) obj[1]);
            carDtoList.add(dto);
        }
        return carDtoList;
    }

    //목록페이지 상품조회
    @Override
    public List<ListCarDto> getPageList(Long id,String maker, String model) {
        //id가 10이면 전체조회 그외는 해당 카테고리로 조회
        List<Object[]> result = new ArrayList<>();
        //전제자동차조회 (그린카매장 메뉴)
        if(id==10){
            //항목검색전
            if(maker.equals("제조사") && model.equals("모델")){
                result = carRepository.getCarList();
            //제조사로 검색
            }else if(!maker.equals("제조사") && model.equals("모델")){
                result = carRepository.getListSearchMaker(maker);
            }
            //제조사모델로 검색
            else if( !maker.equals("제조사") && !model.equals("모델")){
                result = carRepository.getListSearchMakerModel(maker,model);
            }
        }
        //국산차,수입차조회 (국산차, 수입차 메뉴)
        else{
            //항목검색전
            if(maker.equals("제조사") && model.equals("모델")){
                result = carRepository.getCarPageList(id);
            }
            //제조사로 검색
            else if(!maker.equals("제조사") && model.equals("모델")){
                result = carRepository.getListSearchMaker(maker);
            }
            //제조사모델로 검색
            else if( !maker.equals("제조사") && !model.equals("모델")){
                result = carRepository.getListSearchMakerModel(maker,model);
            }
        }
        List<ListCarDto> listCarDto = new ArrayList<>();
        for(Object[] obj: result){
            Car car = (Car) obj[0];
            CarImage carImage = (CarImage) obj[1];
            ListCarDto dto = ListCarDto.builder()
                    .id(car.getId())
                    .year(car.getYear())
                    .title(car.getTitle())
                    .price(car.getPrice())
                    .imgName(carImage.getImgName())
                    .fuel(car.getFuel())
                    .dealer(car.getDealer())
                    .mileage(car.getMileage())
                    .build();
            listCarDto.add(dto);
        }
        return listCarDto;
    }
    
    //딜러별 자동차 조회하기
    @Override
    public List<ListCarDto> getDealerCarList(Long dealerId) {
        List<Object[]> result= carRepository.getDealerIdList(dealerId);
        List<ListCarDto> listCarDto = new ArrayList<>();
        for(Object[] obj: result){
            Car car = (Car) obj[0];
            CarImage carImage = (CarImage) obj[1];
            ListCarDto dto = ListCarDto.builder()
                    .id(car.getId())
                    .year(car.getYear())
                    .title(car.getTitle())
                    .price(car.getPrice())
                    .imgName(carImage.getImgName())
                    .fuel(car.getFuel())
                    .dealer(car.getDealer())
                    .mileage(car.getMileage())
                    .build();
            listCarDto.add(dto);
        }
        return listCarDto;
    }

    @Override
    public void editCar(CarDto dto) {
        //update 엔티티조회 ---> 엔티티값변경 ---> repository.save(엔티티)
        Car car = carRepository.findById(dto.getId()).get();
        car.setCardesc(dto.getCardesc());
        car.setColor(dto.getColor());
        car.setFuel(dto.getFuel());
        car.setPrice(dto.getPrice());
        car.setTransmission(dto.getTransmission());
        car.setTitle(dto.getTitle());
        car.setYear(dto.getYear());
        car.setRegisterNumber(dto.getRegisterNumber());
        car.setMileage(dto.getMileage());
        car.setDisplacement(dto.getDisplacement());
        car.setCategory(categoryRepository.findById(dto.getCategoryId()).get());
        car.setModel(modelRepository.findById(dto.getModelId()).get());
        car.setMaker(makerRepository.findById(dto.getMakerId()).get());
        carRepository.save(car);
    }

    //대분류,중분류,소분류 카테고리 조회 
    @Override
    public CategoryDto getCategory(Long categoryId, Long makerId){
        //categoryId 1또는 2일때는 국산차, 수입차 항목조회
        //그외에는 모든 항목 조회
        CategoryDto result = new CategoryDto();
        List<Category> categories = categoryRepository.findAll();
        List<Maker> makers = (categoryId==1 || categoryId==2) ?
                makerRepository.getList(categoryId)
                : makerRepository.findAll();
        //makerId 0이면 모든 항목조회 아닐때는 해당하는 id만 조회
        List<Model> models = makerId==0? 
                modelRepository.findAll() : 
                modelRepository.getModelList(makerId);
        result.setCategories(categories);
        result.setMakers(makers);
        result.setModels(models);
        return result;
    }
}
