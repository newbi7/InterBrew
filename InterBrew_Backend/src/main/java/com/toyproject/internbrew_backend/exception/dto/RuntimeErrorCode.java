package com.toyproject.internbrew_backend.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public enum RuntimeErrorCode {
        SUCCESS(2000,"",""),
        NOT_MATCHED_TYPE(3200, "EF3200","Trnasfer Number data not matched. please check column fk_transfer_code in tbl_template_initail_data"),
        FAILED_COFFEE_DATA(3201, "EF3201","An unexpected error occurred while entering coffee data. please check in the coffee data"),
        FAILED_MAKE_REDIS_DATA(3202, "EF3202","Failed to generate redis data. Make sure it's duplicates"),
        FAILED_MAKE_COFFEE_DATA(3203, "EF3203","Failed to generate Coffee data."),
        FAILED_LOAD_DATA(3204,"EF3204","Failed to load DATA");

        private final int code;
        private final String errCode;
        private final String errMessage;
}
