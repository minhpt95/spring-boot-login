package com.catdev.project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column
    private String name;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column(unique = true)
    private String identityCard;

    @Column
    private String phoneNumber1;

    @Column
    private String phoneNumber2;

    @Column
    private String currentAddress;

    @Column
    private String permanentAddress;

    @Column
    private String description;

    @Column
    private String accessToken;

    @Column
    private boolean tokenStatus;

    @Column
    private boolean isEnabled;

    @Column
    private Instant createdDate;

    @Column
    private Long createdBy;

    @Column
    private Instant modifiedDate;

    @Column
    private Long modifiedBy;

    @Column
    private String transactionPassword;
}
