package com.catdev.project.service;

import com.catdev.project.readable.request.CreateCVReq;
import com.catdev.project.readable.request.news.CreateRequestHireDeveloperReq;
import com.catdev.project.readable.response.CreateCVRes;

public interface CurriculumVitaeService {
     CreateCVRes createCV(CreateCVReq createCVReq);
}
