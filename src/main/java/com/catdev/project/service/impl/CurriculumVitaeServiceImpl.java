package com.catdev.project.service.impl;

import com.catdev.project.constant.CommonConstant;
import com.catdev.project.entity.CurriculumVitaeEntity;
import com.catdev.project.readable.request.CreateCVReq;
import com.catdev.project.readable.request.news.CreateRequestHireDeveloperReq;
import com.catdev.project.readable.response.CreateCVRes;
import com.catdev.project.respository.CurriculumVitaeRepository;
import com.catdev.project.service.CurriculumVitaeService;
import com.catdev.project.service.S3Service;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor
public class CurriculumVitaeServiceImpl implements CurriculumVitaeService {

    private Environment env;

    private S3Service s3Service;

    private CurriculumVitaeRepository curriculumVitaeRepository;

    @Override
    public CreateCVRes createCV(CreateCVReq createCVReq) {

        String programLanguage = createCVReq.getYourProgramLanguage();

        String extensionFile = FilenameUtils
                .getExtension(createCVReq.getResumeFile().getOriginalFilename())
                .toLowerCase(Locale.ROOT);


        StringBuilder newFileNameStringBuilder = new StringBuilder(CommonConstant.CURRICULUM_VITAE).append("_");
        newFileNameStringBuilder.append(createCVReq.getYourName().replaceAll("\\s+","_"));
        newFileNameStringBuilder.append(".").append(extensionFile);

        StringBuilder pathFileStringBuilder = new StringBuilder(env.getProperty("aws.s3.path.upload.cv"))
                .append(programLanguage.toLowerCase(Locale.ROOT)).append("/")
                .append(extensionFile).append("/")
                .append(newFileNameStringBuilder);

        String pathFile = s3Service.putData(env.getProperty(CommonConstant.AWS_S3_BUCKET),pathFileStringBuilder.toString(),createCVReq.getResumeFile());

        CurriculumVitaeEntity curriculumVitaeEntity = new CurriculumVitaeEntity();
        curriculumVitaeEntity.setCandidateComment(createCVReq.getYourComment());
        curriculumVitaeEntity.setCandidateEmail(createCVReq.getYourEmail());
        curriculumVitaeEntity.setCandidateFilePath(pathFile);
        curriculumVitaeEntity.setCandidateName(createCVReq.getYourName().replaceAll("\\s+"," "));
        curriculumVitaeEntity.setCandidateFileType(extensionFile);
        curriculumVitaeEntity.setCandidateFileName(newFileNameStringBuilder.toString());
        curriculumVitaeEntity.setCandidatePosition(createCVReq.getYourProgramLanguage());
        curriculumVitaeRepository.save(curriculumVitaeEntity);

        CreateCVRes createCVRes = new CreateCVRes();

        return createCVRes;
    }

}
