package com.br.authentication.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResp {
    private Object data;
    public static AuthResp of(Object data) {
        return new AuthResp(data);
    }
}
