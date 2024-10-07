package com.green.car.service;

import com.green.car.entity.Maker;
import com.green.car.entity.Model;
import com.green.car.repository.MakerRepository;
import com.green.car.repository.ModelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
//테스트 어노테이션
@SpringBootTest
public class MakerTest {
    //의존주입 어노테이션
    @Autowired
    public MakerRepository makerRepository;
    @Autowired
    public ModelRepository modelRepository;
    //테스트 메소드 어노테이션
    @Test
    public void getList(){
        List<Maker> result =  makerRepository.getList(2L);
        for(Maker m:result){
            System.out.println(m.toString());
        }
    }
    @Test
    public  void getModellist(){
        List<Model> result =  modelRepository.getModelList(2L);
        for(Model m:result){
            System.out.println(m.toString());
        }
    }
}
