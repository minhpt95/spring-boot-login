package com.catdev.project.entity;

import com.catdev.project.entity.common.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "curriculum_vitae")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurriculumVitaeEntity extends CommonEntity {

    @Column
    private String candidateName;

    @Column(unique = true)
    private String candidateEmail;

    @Column
    private String candidateComment;

    @Column
    private String candidatePosition;

    @Column
    private String candidateFilePath;

    @Column
    private String candidateFileType;

    @Column
    private String candidateFileName;
}
