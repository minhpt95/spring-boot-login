package com.catdev.project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "news")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private Instant createdTime;

    @Column
    private Instant modifiedTime;

    @Column
    private Long createdBy;

    @Column
    private Long modifiedBy;

    @Column
    private boolean active;
}
