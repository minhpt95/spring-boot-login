package com.catdev.project.readable.request;

import liquibase.pro.packaged.A;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonPageRequest<T> {
    private int pageIndex;
    private int pageSize;
    private List<String> sortBy;
    private String sortDirection;
    private T filterObject;
}
