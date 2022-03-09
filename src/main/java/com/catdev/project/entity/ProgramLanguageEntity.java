package com.catdev.project.entity;

import com.catdev.project.entity.common.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "program_language")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProgramLanguageEntity extends CommonEntity {
    @Column
    private String programLanguageName;

    @Column(unique = true)
    private String programLanguageCode;

    @Column
    private boolean available;
}