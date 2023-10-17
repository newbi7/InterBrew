package com.toyproject.internbrew_backend.inquiry.repository;

import com.toyproject.internbrew_backend.inquiry.dto.InquiryDto;
import com.toyproject.internbrew_backend.inquiry.dto.InquiryListDto;
import com.toyproject.internbrew_backend.inquiry.entity.Inquiry;

import java.util.List;

public interface InquiryRepositoryCustom {

    List<InquiryListDto> findAllInquiryWithUserId();

    List<InquiryListDto> findByStatus(String status);

    Long findTheNumberOfUnsolved();
}
