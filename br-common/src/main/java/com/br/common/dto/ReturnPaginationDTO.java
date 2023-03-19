package com.br.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReturnPaginationDTO <T> {
    int totalElements;

    int totalPages;

    int pageNumber;

    List<T> content = new ArrayList<>();
}
