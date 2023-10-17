package com.toyproject.internbrew_backend.inquiry.repository;

import com.toyproject.internbrew_backend.inquiry.dto.InquiryDto;
import com.toyproject.internbrew_backend.inquiry.dto.InquiryListDto;
import com.toyproject.internbrew_backend.inquiry.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, String>, InquiryRepositoryCustom{

    Inquiry findByInquiryNo(String inquiryNo);

}
