package com.green.car.repository;

import com.green.car.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car,Long> {


    @Query("select c, i from Car c left outer join CarImage i on i.car = c " +
            "where i.repimgYn='Y'")
    List<Object[]> getCarList();

    //메인페이지에 자동차조회
    @Query("select c, i from Car c left outer join CarImage i on i.car = c " +
            "where i.repimgYn='Y' and c.category.id=:id")
    Page<Object[]> getCarMainList(Pageable pageable, @Param("id") Long id);

    //페이지에 자동차조회
    @Query("select c, i from Car c left outer join CarImage i on i.car = c " +
            "where i.repimgYn='Y' and c.category.id=:id")
    List<Object[]> getCarPageList(@Param("id") Long id);

    //제조사 이름으로 자동차조회 (그린카매장에서 제조사를 기준으로 목록조회)
    @Query("select c, i from Car c left outer join CarImage i on i.car = c " +
            "where i.repimgYn='Y' and c.maker.makerName=:maker")
    List<Object[]> getListSearchMaker(@Param("maker") String maker);

    //제조사이름과 모델명이 일치하는 자동차 조회(그린카매장 페이지에서 제조사이름 모델이름으로 조회)
    @Query("select c, i from Car c left outer join CarImage i on i.car = c " +
            "where i.repimgYn='Y' and c.maker.makerName=:maker " +
            "and c.model.modelName=:model")
    List<Object[]> getListSearchMakerModel(@Param("maker") String maker,
                                           @Param("model") String model);

    //딜러id로 자동차조회 (딜러권한)
    @Query("select c, i from Car c left outer join CarImage i on i.car = c " +
            "where i.repimgYn='Y' and c.dealer.id = :dealerId")
    List<Object[]> getDealerIdList(@Param("dealerId") Long dealerId);
}
