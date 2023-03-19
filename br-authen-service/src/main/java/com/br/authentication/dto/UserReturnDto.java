package com.br.authentication.dto;

import com.br.common.dto.BaseReturnDto;
import lombok.Data;

@Data
public class UserReturnDto extends BaseReturnDto {
    private String phone;
    private String email;
    private String lastActive;
    private String status;
    private String userName;
}
