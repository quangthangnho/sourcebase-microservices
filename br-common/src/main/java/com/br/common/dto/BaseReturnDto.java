package com.br.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseReturnDto {
    private Long id;
    private ZonedDateTime createdDate;
    private ZonedDateTime updatedDate;
    private String createdBy;
    private String updatedBy;
}
