package com.catdev.project.entity;

import com.catdev.project.entity.common.DateTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "country")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AirportEntity extends DateTimeEntity {
}
