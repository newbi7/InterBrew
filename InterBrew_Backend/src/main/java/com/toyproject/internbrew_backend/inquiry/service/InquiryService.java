package com.toyproject.internbrew_backend.inquiry.service;

import com.toyproject.internbrew_backend.inquiry.dto.InquiryDto;
import com.toyproject.internbrew_backend.inquiry.dto.InquiryListDto;
import com.toyproject.internbrew_backend.inquiry.dto.InquiryUserDto;
import com.toyproject.internbrew_backend.inquiry.entity.Inquiry;
import com.toyproject.internbrew_backend.inquiry.repository.InquiryRepository;
import com.toyproject.internbrew_backend.user.entity.User;
import com.toyproject.internbrew_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    private final UserRepository userRepository;


    /*
    건의사항 생성
    생성날자 자동입력 (날짜만)
     */
    public void createInquiry(InquiryDto inquiryDto) {

        InquiryUserDto inquiryUserDto = new InquiryUserDto();

        String userId = inquiryDto.getUserId();
        User user = userRepository.findByUserId(userId).get();

        inquiryUserDto.setUser(user);
        inquiryUserDto.setInquiryContents(inquiryDto.getInquiryContents());
        inquiryUserDto.setInquiryCreatedAt(LocalDateTime.now());

        Inquiry inquiry = Inquiry.on(inquiryUserDto);
        inquiryRepository.save(inquiry);

    }


    /**
     * 관리자 전체 건의사항 조회
     * @return
     */
    public List<InquiryListDto> findInquiries() {
        return inquiryRepository.findAllInquiryWithUserId();
    }

    /**
     * 관리자 건의사항 상태별 조회
     *  -> 상태 구분 - 처리 / 미처리
     * @param status
     * @return
     */
    public List<InquiryListDto> findInquiriesByStatus(String status) {
        return inquiryRepository.findByStatus(status);
    }

    /**
     * 관리자 미처리 건의사항 통계
     * @return
     */
    public Long getTheNumOfUnsolvedInquiry(){
        return inquiryRepository.findTheNumberOfUnsolved();
    }

    /**
     * 관리자 건의사항 상태 수정
     *  -> JPA dirty checking활용
     * @param inquiryNo
     * @return
     */
    @Transactional
    public Inquiry updateInquiryStatus(String inquiryNo) {
        Inquiry findInquiry = inquiryRepository.findByInquiryNo(inquiryNo);
        if (findInquiry == null) {
            throw new RuntimeException("요청사항 찾을 수 없음 inquiryNo: " + inquiryNo);
        }
        findInquiry.changeStatus("N");
        return findInquiry;
    }
}
