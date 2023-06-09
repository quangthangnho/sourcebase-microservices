package com.br.common.exception;

import com.br.common.result.ResultEntity;
import com.br.common.utils.LogUtils;

public interface IErrorResult {
    default ResultEntity error(Exception ex) {
        LogUtils.getInstance().error(ex);
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setMessage(ex.getMessage());
        if (ex instanceof CustomException) {
            CustomException customException = (CustomException) ex;
            resultEntity.setCode(customException.getCode());
            resultEntity.setData(customException.getData());
        } else {
            resultEntity.setCode(500);
            resultEntity.setMessage("Internal Server Error " + ex.getMessage());
        }
        return resultEntity;
    }
}
