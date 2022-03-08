package com.catdev.project.entity;

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
public class CurriculumVitaeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

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
