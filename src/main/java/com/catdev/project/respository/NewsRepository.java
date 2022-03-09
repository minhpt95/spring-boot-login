package com.catdev.project.respository;

import com.catdev.project.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NewsRepository extends JpaRepository<NewsEntity,Long> , JpaSpecificationExecutor<NewsEntity> {

}
