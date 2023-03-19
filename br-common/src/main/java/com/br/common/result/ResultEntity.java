package com.br.common.result;

import com.br.common.constants.MessageConst;

public class ResultEntity {
    private Integer code;
    private String message;
    private Object data;

    public ResultEntity() {
        this.code = 0;
        this.message = "no message";
    }

    public ResultEntity(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResultEntity returnSuccessResult(Object data) {
        return new ResultEntity(1, MessageConst.SUCCESS, data);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
