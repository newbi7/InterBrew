package com.toyproject.internbrew_backend.exception;

import com.toyproject.internbrew_backend.exception.dto.RuntimeErrorCode;
import lombok.Getter;

@Getter
public class DataRuntimeException extends RuntimeException {
    private int code;
    private String errCode;
    private String errMessage;

    public DataRuntimeException(RuntimeErrorCode nullErrorCode){
        this.code = nullErrorCode.getCode();
        this.errCode = nullErrorCode.getErrCode();
        this.errMessage = nullErrorCode.getErrMessage();
    }

    public DataRuntimeException(int code, String errCode, String errMessage) {
    }
}
