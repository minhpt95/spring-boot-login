package com.catdev.project.entity;

import com.catdev.project.entity.common.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "topic")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopicEntity extends CommonEntity {
    @Column
    private String topicName;

    @Column
    private String topicCode;

    @Column
    private boolean active;
}
