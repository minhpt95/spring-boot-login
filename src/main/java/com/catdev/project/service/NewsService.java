package com.catdev.project.service;

import com.catdev.project.dto.ListResponseDto;
import com.catdev.project.readable.request.CommonPageRequest;
import com.catdev.project.readable.request.news.CreateNewsReq;
import com.catdev.project.readable.response.news.CreateNewsRes;
import com.catdev.project.readable.response.news.NewsPageableRes;

public interface NewsService{
    ListResponseDto<NewsPageableRes> pageableNews(CommonPageRequest commonPageRequest);

    CreateNewsRes createNews(CreateNewsReq createNewsReq);
}
