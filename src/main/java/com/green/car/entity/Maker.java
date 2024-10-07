package com.green.car.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name="maker_seq", sequenceName = "maker_seq", allocationSize = 1, initialValue = 1)
public class Maker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "maker_seq")
    @Column(name="maker_id")
    private Long id;
    private String makerName;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
}
