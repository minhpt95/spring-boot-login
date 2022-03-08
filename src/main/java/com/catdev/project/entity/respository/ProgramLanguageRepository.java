package com.catdev.project.entity.respository;

import com.catdev.project.entity.ProgramLanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProgramLanguageRepository extends JpaRepository<ProgramLanguageEntity,Long>, JpaSpecificationExecutor<ProgramLanguageEntity> {

}
