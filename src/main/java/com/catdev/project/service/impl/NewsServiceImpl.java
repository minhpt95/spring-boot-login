package com.catdev.project.service.impl;

import com.catdev.project.respository.EmployeeRepository;
import com.catdev.project.respository.NewsRepository;
import com.catdev.project.service.NewsService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class NewsServiceImpl implements NewsService {
    private NewsRepository newsRepository;
    private EmployeeRepository employeeRepository;

    public void pageableNews(){

    }

    public void createNews(){

    };

    public void updateNews(){

    };

    public void deleteNews(){

    };

}
