package com.catdev.project.entity;

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
public class ProgramLanguageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column
    private String programLanguageName;

    @Column(unique = true)
    private String programLanguageCode;

    @Column
    private boolean available;
}