package com.catdev.project.entity;

import com.catdev.project.entity.common.DateTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "country")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryEntity extends DateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(updatable = false, nullable = false,length = 2)
    private String countryCode;

    @Column(length = 45)
    private String name;
}
