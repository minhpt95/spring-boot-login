package com.catdev.project.readable.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCVReq {
    private String yourName;
    private String yourEmail;
    private String yourComment;
    private String yourProgramLanguage;
    private MultipartFile resumeFile;
}
