package com.catdev.project.service.impl;

import com.catdev.project.dto.ListResponseDto;
import com.catdev.project.dto.employee.EmployeeAuthorDto;
import com.catdev.project.entity.NewsEntity;
import com.catdev.project.entity.common.CommonEntity;
import com.catdev.project.readable.request.CommonPageRequest;
import com.catdev.project.readable.request.news.CreateNewsReq;
import com.catdev.project.readable.response.news.CreateNewsRes;
import com.catdev.project.readable.response.news.NewsPageableRes;
import com.catdev.project.respository.EmployeeRepository;
import com.catdev.project.respository.NewsRepository;
import com.catdev.project.service.NewsService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class NewsServiceImpl implements NewsService {
    private NewsRepository newsRepository;
    private EmployeeRepository employeeRepository;

    @Override
    public ListResponseDto<NewsPageableRes> pageableNews(CommonPageRequest commonPageRequest){

        var listOrder = List.of(
                new Sort.Order(Sort.Direction.fromString(commonPageRequest.getSortDirection()),"id", Sort.NullHandling.NULLS_LAST)
        );

        var order = Sort.by(listOrder);

        var pageRequest = PageRequest.of(commonPageRequest.getPageIndex(),commonPageRequest.getPageSize(),order);

        var newsEntityPage = newsRepository.findAll(pageRequest);

        if(newsEntityPage.isEmpty()){
            return new ListResponseDto<>();
        }

        var createBySet = newsEntityPage.map(CommonEntity::getCreatedBy).toSet();

        var createByEntities = employeeRepository.findAllById(createBySet);

        var newsPageableResPage = newsEntityPage.map(newsEntity -> {
            NewsPageableRes newsPageableRes = new NewsPageableRes();
            newsPageableRes.setId(newsEntity.getId());
            newsPageableRes.setTitle(newsEntity.getTitle());
            newsPageableRes.setContent(newsEntity.getContent());
            newsPageableRes.setCreatedTime(newsEntity.getCreatedTime());

            var createByOptional = createByEntities
                    .stream()
                    .filter(createBy -> createBy.getId().equals(newsEntity.getCreatedBy()))
                    .map(createByEntity -> {
                        EmployeeAuthorDto employeeAuthorDto = new EmployeeAuthorDto();
                        employeeAuthorDto.setEmail(createByEntity.getEmail());
                        employeeAuthorDto.setName(createByEntity.getName());
                        employeeAuthorDto.setId(createByEntity.getId());
                        return employeeAuthorDto;
                    })
                    .findFirst();



            newsPageableRes.setCreatedBy(createByOptional.orElse(null));

            return newsPageableRes;
        });

        return new ListResponseDto<NewsPageableRes>().buildResponseList(newsPageableResPage,commonPageRequest.getPageIndex(),commonPageRequest.getPageSize());
    }

    @Override
    public CreateNewsRes createNews(CreateNewsReq createNewsReq) {

        var createNewsEntity = new NewsEntity();

        createNewsEntity.setTitle(createNewsReq.getTitle());
        createNewsEntity.setContent(createNewsEntity.getContent());
        createNewsEntity.setCreatedBy(createNewsEntity.getCreatedBy());
        createNewsEntity.setActive(true);
        createNewsEntity.setCreatedTime(Instant.now());

        createNewsEntity = newsRepository.save(createNewsEntity);

        var createNewsRes = new CreateNewsRes();
        createNewsRes.setTitle(createNewsEntity.getTitle());
        createNewsRes.setActive(createNewsEntity.isActive());
        createNewsRes.setContent(createNewsEntity.getContent());
        createNewsRes.setCreatedDate(createNewsEntity.getCreatedTime());

        EmployeeAuthorDto employeeAuthorDto = new EmployeeAuthorDto();

        var employeeAuthorEntity = employeeRepository.findById(createNewsRes.getId());

        if(employeeAuthorEntity.isPresent()){
            employeeAuthorDto.setId(employeeAuthorEntity.get().getId());
            employeeAuthorDto.setName(employeeAuthorEntity.get().getName());
            employeeAuthorDto.setEmail(employeeAuthorEntity.get().getEmail());
        }

        createNewsRes.setCreatedBy(employeeAuthorDto);

        return createNewsRes;
    }

    public void updateNews(){

    };

    public void deleteNews(){

    };

}
