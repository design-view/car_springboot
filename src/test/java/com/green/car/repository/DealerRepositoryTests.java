package com.green.car.repository;

import com.green.car.entity.Car;
import com.green.car.entity.Dealer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DealerRepositoryTests {
    @Autowired
    DealerRepository dealerRepository;

    @Test
    public void insertDealerTest(){
        Dealer dealer = Dealer.builder()
                .name("홍길동")
                .phone("01012341234")
                .location("울산 북구")
                .build();
        Car car = Car.builder()


                .year(2000)
                .color("검정색")
                .price(1800)
                .dealer(dealer)
                .registerNumber("12345678978")
                .build();
        List<Car> list = new ArrayList<>();
        list.add(car);
        dealer.setCars(list);
        dealerRepository.save(dealer);
    }
}
