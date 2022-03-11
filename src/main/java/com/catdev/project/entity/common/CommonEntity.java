package com.catdev.project.entity.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Instant createdTime;

    @Column(nullable = false)
    private Long createdBy;

    @Column
    private Instant modifiedTime;

    @Column
    private Long modifiedBy;
}
