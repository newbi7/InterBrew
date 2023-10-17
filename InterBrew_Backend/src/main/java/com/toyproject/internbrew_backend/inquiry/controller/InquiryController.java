package com.toyproject.internbrew_backend.inquiry.controller;

import com.toyproject.internbrew_backend.exception.DataNullException;
import com.toyproject.internbrew_backend.exception.DataRuntimeException;
import com.toyproject.internbrew_backend.inquiry.dto.InquiryDto;
import com.toyproject.internbrew_backend.inquiry.dto.InquiryListDto;
import com.toyproject.internbrew_backend.inquiry.service.InquiryService;
import com.toyproject.internbrew_backend.redis.RedisPublisher;
import com.toyproject.internbrew_backend.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.toyproject.internbrew_backend.exception.dto.NullErrorCode.NULL_DATA;
import static com.toyproject.internbrew_backend.exception.dto.RuntimeErrorCode.FAILED_LOAD_DATA;

@RestController
@RequestMapping(value = "api/v1")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class InquiryController {

    private final InquiryService inquiryService;

    private final RedisPublisher redisPublisher;

    /*
    건의사항 생성
    생성날자 자동입력
    에러코드 지정필요
     */
    @PutMapping(value = "inquiry/mypage")
    public ResponseEntity<?> createInquiry(@RequestBody InquiryDto inquiryDto) {

        try {
            inquiryService.createInquiry(inquiryDto);
            String response = new String("Inquiry, Inquiry create successfully.");
            redisPublisher.sendInquiryAlarm(inquiryDto.getUserId(), "/topic/alarm");
            return ResponseEntity.ok(response);
        } catch (DataNullException e) {
            log.error("Inquiry Deletion Null Error");
            DataNullException response = new DataNullException(4102, "EF4102", "Inquiry, Inquiry Data Null");
            return ResponseEntity.ok().body(response);
        } catch (DataRuntimeException e) {
            log.error("Inquiry Deletion Runtime Error");
            DataRuntimeException response = new DataRuntimeException(4102, "EF4102", "Inquiry, Inquiry Data Runtime Error");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            log.error("Inquiry Deletion Error");
            Exception response = new Exception("Inquiry Deletion Error", e);
            return ResponseEntity.ok().body(response);
        }
    }

    /**
     * 관리자 전체 건의 사항 조회
     * @return
     */
    @GetMapping("/inquiry/admin")
    public ResponseEntity<Object> getInquiryList() {
        try {
            log.debug("getInquiryList");
            List<InquiryListDto> result = inquiryService.findInquiries();
            return ResponseEntity.ok(result);
        } catch (DataNullException e) {
            log.error("Load Data Null Exception");
            DataNullException response = new DataNullException(NULL_DATA);
            return ResponseEntity.ok(response);
        } catch (DataRuntimeException e) {
            log.error("Load Data Runtime Exception");
            DataRuntimeException response = new DataRuntimeException(FAILED_LOAD_DATA);
            return ResponseEntity.ok(response);
        }
    }

    /**
     * 관리자 건의사항 상태별 조회
     *  -> 상태 구분(inquitryUserStatus) : 처리 (N) / 미처리 (Y)
     * @param status
     * @return
     */
    @GetMapping("/inquiry/admin/{status}")
    public ResponseEntity<Object> getInquiryListByStatus(@PathVariable String status) {
        try {
            log.debug("getInquiryListByStatus");
            List<InquiryListDto> result = inquiryService.findInquiriesByStatus(status);
            return ResponseEntity.ok(result);
        } catch (DataNullException e) {
            log.error("Load Data Null Exception");
            DataNullException response = new DataNullException(NULL_DATA);
            return ResponseEntity.ok(response);
        } catch (DataRuntimeException e) {
            log.error("Load Data Runtime Exception");
            DataRuntimeException response = new DataRuntimeException(FAILED_LOAD_DATA);
            return ResponseEntity.ok(response);
        }
    }

    /**
     * 관리자 미처리 건의사항 통계
     * @return
     */
    @GetMapping("/inquiry/admin/statistics")
    public ResponseEntity<Object> getTheNumOfUnsolvedInquiry() {
        try {
            log.debug("getTheNumOfUnsolvedInquiry");
            Long result = inquiryService.getTheNumOfUnsolvedInquiry();
            return ResponseEntity.ok(result);
        } catch (DataRuntimeException e) {
            log.error("Load Data Runtime Exception");
            DataRuntimeException response = new DataRuntimeException(FAILED_LOAD_DATA);
            return ResponseEntity.ok(response);
        }
    }

    /**
     * 관리자 건의사항 상태 수정
     * @param inquiryDto
     * @return
     */
    @PostMapping(value = "/inquiry/admin")
    public ResponseEntity<Object> updateInquiryStatus(@RequestBody InquiryDto inquiryDto){
        log.debug("updateInqiryStatus");
        String inquiryNo = inquiryDto.getInquiryNo();
        try {
            inquiryService.updateInquiryStatus(inquiryNo);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            log.error("Update Inquiry Status Error");
            Exception response = new Exception("Update Inquiry Status Error", e);
            return ResponseEntity.ok(response);
        }
    }
}
