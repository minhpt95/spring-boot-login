package com.catdev.project.entity.respository;

import com.catdev.project.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> , JpaSpecificationExecutor<EmployeeRepository> {
}
