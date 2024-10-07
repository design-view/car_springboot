package com.green.car.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = "cars")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name="dealer_seq", sequenceName = "dealer_seq",
        allocationSize = 1, initialValue = 1)
//직렬화 프로세스중에 cars필드를 무시하게 변경
//(하이버네이트가 생성한 필드를 무시하도록 설정)
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Dealer {
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "dealer_seq")
    @Column(name="dealer_id")
    @Id
    private Long id;
    private String name;
    private String phone;
    private String location;
    @OneToOne
    @JoinColumn(name="member_id")
    private Member member;
    @JsonIgnore
    @OneToMany(mappedBy = "dealer", cascade = CascadeType.ALL)
    private List<Car> cars = new ArrayList<>();
}
