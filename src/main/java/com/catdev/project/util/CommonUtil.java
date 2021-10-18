package com.catdev.project.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


public class CommonUtil {


    public static Pageable buildPageable(int pageIndex, int pageSize, String sortColumn, String sortDirection) {
        return PageRequest.of(pageIndex, pageSize);
    }



}
