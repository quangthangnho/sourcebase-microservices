package com.br.authentication.dto;

import lombok.Data;

@Data
public class ProfileReturnDto {
    private UserReturnDto userId;
    private String fullName;
    private String dob;
    private  String idCard;
}
