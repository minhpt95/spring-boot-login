package com.catdev.project.entity;

import com.catdev.project.entity.common.DateTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "airport")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AirportEntity extends DateTimeEntity {
    @Id
    @Column(nullable = false,length = 3)
    private String airportCode;

    @Column
    private String name;

    @Column
    private String city;
}
