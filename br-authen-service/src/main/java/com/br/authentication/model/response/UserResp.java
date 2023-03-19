package com.br.authentication.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResp {
    private Object data;
    public static UserResp of(Object data) {
        return new UserResp(data);
    }
}
