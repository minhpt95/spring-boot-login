package com.catdev.project.entity.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NewsRepository extends JpaRepository<NewsRepository,Long> , JpaSpecificationExecutor<NewsRepository> {

}
