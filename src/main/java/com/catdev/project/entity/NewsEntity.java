package com.catdev.project.entity;

import com.catdev.project.entity.common.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import javax.persistence.*;

@Entity
@Table(name = "news")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Polymorphism(type = PolymorphismType.EXPLICIT)
public class NewsEntity extends CommonEntity {
    @Column
    private String title;

    @Column
    private String content;

    @Column
    private boolean active;
}
