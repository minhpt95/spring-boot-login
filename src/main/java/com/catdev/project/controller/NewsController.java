package com.catdev.project.controller;

import com.catdev.project.constant.ErrorConstant;
import com.catdev.project.dto.ListResponseDto;
import com.catdev.project.dto.ResponseDto;
import com.catdev.project.readable.request.CommonPageRequest;
import com.catdev.project.readable.request.news.CreateNewsReq;
import com.catdev.project.readable.response.news.CreateNewsRes;
import com.catdev.project.readable.response.news.NewsPageableRes;
import com.catdev.project.service.NewsService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("/api/news")
public class NewsController {

    private NewsService newsService;

    @GetMapping("/getPage")
    public ResponseDto<ListResponseDto<NewsPageableRes>> getNewsPage(@RequestBody CommonPageRequest commonPageRequest){
        var newsPageable = newsService.pageableNews(commonPageRequest);

        var responseDto = new ResponseDto<ListResponseDto<NewsPageableRes>>();

        responseDto.setContent(newsPageable);
        responseDto.setMessage(ErrorConstant.Message.SUCCESS);
        responseDto.setErrorType(ErrorConstant.Type.SUCCESS);
        responseDto.setErrorCode(ErrorConstant.Code.SUCCESS);

        return responseDto;
    }

    @GetMapping("/create")
    public ResponseDto<CreateNewsRes> createNews(@RequestBody CreateNewsReq createNewReq){
        var newsPageable = newsService.createNews(createNewReq);

        var responseDto = new ResponseDto<CreateNewsRes>();

        responseDto.setContent(newsPageable);
        responseDto.setMessage(ErrorConstant.Message.SUCCESS);
        responseDto.setErrorType(ErrorConstant.Type.SUCCESS);
        responseDto.setErrorCode(ErrorConstant.Code.SUCCESS);

        return responseDto;
    }

}
