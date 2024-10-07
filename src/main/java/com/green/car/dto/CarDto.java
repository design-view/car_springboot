package com.green.car.dto;

import com.green.car.entity.Category;
import com.green.car.entity.Dealer;
import com.green.car.entity.Maker;
import com.green.car.entity.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
    private Long id;
    private Long categoryId;
    private Long makerId;
    private Long modelId;
    private List<CarImageDto> carImageDtos = new ArrayList<>();
    private int displacement;  //배기량
    private int mileage; //주행거리
    private String transmission; //변속기
    private String fuel; //연료
    private String color, registerNumber;  //색상,등록번호
    private int year,price;  //년식, 가격
    private String title; //매물제목
    private String cardesc; //설명
    private Long dealerId; //딜러id

    private Dealer dealer; //딜러

    public void update(Long categoryId, Long makerId, Long modelId, String color,
                       String registerNumber, int year, int price,
                       Long id,int displacement, int mileage, String transmission,
                       String fuel, String title, String cardesc){
        this.categoryId = categoryId;
        this.makerId = makerId;
        this.modelId = modelId;
        this.color = color;
        this.registerNumber = registerNumber;
        this.price = price;
        this.year = year;
        this.id = id;
        this.displacement = displacement;
        this.transmission = transmission;
        this.mileage = mileage;
        this.fuel =fuel;
        this.title = title;
        this.cardesc = cardesc;
    }
}
