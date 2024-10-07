package com.green.car.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DealerRegListDto {
    private Long id;
    private String name;
    private String phone;
    private String location;
    private String message;
    private Long memberId;
    private String dealerState;
}
