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
public class InquiryDto {

    private String inquiryNo;
    private String userNo;
    private String userId;
    private String inquiryContents;
    private LocalDateTime inquiryCreatedAt;
    private LocalDateTime inquiryUpdatedAt;
    private String inquiryStatus;

    public InquiryDto(Inquiry inquiry, User user) {
        this.inquiryNo = inquiry.getInquiryNo();
        this.userId = user.getUserId();
        this.inquiryContents = inquiry.getInquiryContents();
        this.inquiryCreatedAt = inquiry.getInquiryCreatedAt();
        this.inquiryUpdatedAt = inquiry.getInquiryUpdatedAt();
        this.inquiryStatus = inquiry.getInquiryUserStatus();
    }
}
