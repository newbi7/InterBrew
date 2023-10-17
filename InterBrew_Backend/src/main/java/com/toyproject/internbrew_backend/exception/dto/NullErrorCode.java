package com.toyproject.internbrew_backend.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NullErrorCode {
    SUCCESS(2000,"",""),
    COFFEE_NOT_ENOUGH(4100, "EF4100","COFFEE isn't exist. please check database coffee"),
    USER_NOT_EXIST(4101,"EF4101","USER isn't exist. please check database user"),
    PAYMENT_NOT_EXIST(4102,"EF4102","PAYMENT isn't exist. please check database payment"),
    COFFEE_NOT_CREATE(4103,"EF4103","COFFEE INSERT DATA isnt` exist."),
    NULL_DATA(4104,"EF4104","Data that is absent or lacking a value");


    private final int code;
    private final String errCode;
    private final String errMessage;
}
