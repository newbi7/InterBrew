package com.toyproject.internbrew_backend.inquiry.dto;

import com.toyproject.internbrew_backend.inquiry.entity.Inquiry;
import com.toyproject.internbrew_backend.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class InquiryUserDto {

    private String inquiryNo;
    private User user;
    private String inquiryContents;
    private LocalDateTime inquiryCreatedAt;
    private LocalDateTime inquiryUpdatedAt;
    private String inquiryUserCreate;
    private String inquiryUserUpdate;
    private String inquiryUserStatus;

    public InquiryUserDto(Inquiry inquiry, User user) {
        this.inquiryNo = inquiry.getInquiryNo();
        this.user = user;
        this.inquiryContents = inquiry.getInquiryContents();
        this.inquiryCreatedAt = inquiry.getInquiryCreatedAt();
        this.inquiryUpdatedAt = inquiry.getInquiryUpdatedAt();
        this.inquiryUserStatus = inquiry.getInquiryUserStatus();
    }
}
