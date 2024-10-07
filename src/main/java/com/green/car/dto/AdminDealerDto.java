package com.green.car.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdminDealerDto {
    private String name;
    private String phone;
    private String location;
    private Long memberid;
}
