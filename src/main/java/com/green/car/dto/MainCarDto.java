package com.green.car.dto;

import lombok.*;

@Builder
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainCarDto {
    private Long id;
    private int price;
    private String makerName;
    private String imgName;
    private String title;
}
