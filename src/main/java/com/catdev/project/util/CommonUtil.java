package com.catdev.project.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;


public class CommonUtil {


    public static Pageable buildPageable(int pageIndex, int pageSize) {
        return PageRequest.of(pageIndex, pageSize);
    }

    public static Pageable buildPageable(int pageIndex, int pageSize,Direction direction,String... properties) {
        return PageRequest.of(pageIndex, pageSize,direction,properties);
    }

}
