package com.green.car.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Stack;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name="carimg_seq", sequenceName = "carimg_seq",
allocationSize = 1, initialValue = 1)
public class CarImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "carimg_seq")
    @Column(name="car_img_id")
    private Long id;
    private String imgName;  //이미지이름
    private String oriImgName;   //원본이름
    private  String imgUrl;      //경로
    private  String repimgYn;    //대표이미지여부
    //다대일관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_id")
    private Car car;

    public void updata(String oriImgName, String imgName, String imgUrl){
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
    }
}
