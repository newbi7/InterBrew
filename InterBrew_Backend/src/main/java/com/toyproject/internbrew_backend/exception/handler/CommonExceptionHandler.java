package com.toyproject.internbrew_backend.exception.handler;

import com.toyproject.internbrew_backend.exception.DataNullException;
import com.toyproject.internbrew_backend.exception.DataRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerException(Exception e){
        log.error("handlerException : ", e);
        Exception response = new Exception(e);
        return ResponseEntity.ok().body(response);
    }

    @ExceptionHandler(DataNullException.class)
    public ResponseEntity<?> handlerFactoryException(DataNullException e){
        log.error("DataNullException : ", e.getMessage());
        DataNullException response = new DataNullException(e.getCode(), e.getErrCode(), e.getErrMessage());
        return ResponseEntity.ok().body(response);
    }

    @ExceptionHandler(DataRuntimeException.class)
    public ResponseEntity<?> handlerFactoryException(DataRuntimeException e){
        log.error("DataRuntimeException : ", e.getMessage());
        DataRuntimeException response = new DataRuntimeException(e.getCode(), e.getErrCode(), e.getErrMessage());
        return ResponseEntity.ok().body(response);
    }
}
