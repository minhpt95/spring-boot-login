package com.catdev.project.respository;

import com.catdev.project.entity.CurriculumVitaeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface CurriculumVitaeRepository extends JpaRepository<CurriculumVitaeEntity, Long> {
}
