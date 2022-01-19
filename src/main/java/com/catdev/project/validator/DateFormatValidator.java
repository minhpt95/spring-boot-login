package com.catdev.project.validator;

import com.catdev.project.constant.DateConstant;
import com.catdev.project.validator.anotation.DateFormatConstraint;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;

/**
 * @author itsol.hungtt on 12/25/2020
 * Copyright @DONG.NV
 */
public class DateFormatValidator implements ConstraintValidator<DateFormatConstraint, String> {

    @Override
    public void initialize(DateFormatConstraint dateFormatConstraint) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext ct) {
       if(StringUtils.isBlank(s)){
           return false;
       }

       SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
       simpleDateFormat.setLenient(true);

       return true;

    }

}
