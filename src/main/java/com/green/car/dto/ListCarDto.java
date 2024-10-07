package com.green.car.dto;

import com.green.car.entity.Dealer;
import lombok.*;

@Builder
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCarDto {
    private Long id;
    private String title; //매물제목
    private int year; //년식
    private String fuel;  //연료
    private int price;  //가격
    private Dealer dealer;  //딜러정보
    private String imgName;   //이미지경로
    private int mileage;      //주행거리
}
