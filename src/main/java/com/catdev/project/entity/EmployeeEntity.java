package com.catdev.project.entity;

import com.catdev.project.entity.common.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity extends CommonEntity {
    @Column
    private String name;

    @Column(unique = true)
    private String email;

    @Column
    private String imageUrl;

    @Column
    private String position;

    @Column
    private String linkedinUrl;

    @Column
    private String facebookUrl;

    @Column
    private boolean active;
}
