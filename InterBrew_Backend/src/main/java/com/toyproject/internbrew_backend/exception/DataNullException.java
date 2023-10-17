package com.toyproject.internbrew_backend.exception;

import com.toyproject.internbrew_backend.exception.dto.NullErrorCode;
import lombok.Getter;

@Getter
public class DataNullException extends NullPointerException{
    
    private int code;
    private String errCode;
    private String errMessage;

    public DataNullException(NullErrorCode nullErrorCode){
        this.code = nullErrorCode.getCode();
        this.errCode = nullErrorCode.getErrCode();
        this.errMessage = nullErrorCode.getErrMessage();
    }

    public DataNullException(int code, String errCode, String errMessage) {
    }

}
