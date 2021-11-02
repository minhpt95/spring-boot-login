package com.catdev.project.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "cus_sell")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSellEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column
    private Long quantity;
}
