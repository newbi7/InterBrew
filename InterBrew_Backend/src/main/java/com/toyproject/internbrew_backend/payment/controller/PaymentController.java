package com.toyproject.internbrew_backend.payment.controller;

import com.toyproject.internbrew_backend.exception.DataNullException;
import com.toyproject.internbrew_backend.exception.DataRuntimeException;
import com.toyproject.internbrew_backend.payment.dto.PaymentListDto;
import com.toyproject.internbrew_backend.payment.dto.PaymentMonthlyTotalDto;
import com.toyproject.internbrew_backend.payment.dto.PaymentStatisticDto;
import com.toyproject.internbrew_backend.payment.dto.PaymentUserDto;
import com.toyproject.internbrew_backend.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.toyproject.internbrew_backend.exception.dto.NullErrorCode.NULL_DATA;
import static com.toyproject.internbrew_backend.exception.dto.RuntimeErrorCode.FAILED_LOAD_DATA;

@RestController
@RequestMapping(value="api/v1")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class PaymentController {

    private final RedisTemplate<String, Object> redisTemplate;

    private final PaymentService paymentService;

    /*
    2023-08-09
    주문시 레디스에 저장
    */
    @PutMapping("/payment/redis")
    public ResponseEntity<?> createPaymentRedis(@RequestBody Object redis) throws Exception {
        try {
            paymentService.createPaymentRedis(redis);
            String response = new String ("Payment saved in Redis successfully.");
            return ResponseEntity.ok().body(response);
        } catch (DataNullException e) {
            log.error("Payment Redis Null Error");
            DataNullException response = new DataNullException(4102, "EF4102", "Payment Redis Data Null");
            return ResponseEntity.ok().body(response);
        } catch (DataRuntimeException e) {
            log.error("Payment Redis Runtime Error");
            DataRuntimeException response = new DataRuntimeException(4102, "EF4102", "Payment Redis Data Runtime Error");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            log.error("Payment Redis Saving Error");
            Exception response = new Exception("Payment Redis Saving Error", e);
            return ResponseEntity.ok().body(response);
        }
    }

    /*
    2023-08-09
    레디스에 저장된 정보를 payment DB, paymentItem DB 에 업데이트
    */
    @PutMapping("/payment")
    public ResponseEntity<?> createPayment() throws Exception {
        try {
            Object orderDetail = redisTemplate.opsForValue().get("order:");
            String paymentNo = paymentService.createPayment(orderDetail);
            paymentService.createPaymentId(orderDetail, paymentNo);
            String response = new String("Payment, PaymentItem created successfully.");
            return ResponseEntity.ok().body(response);
        } catch (DataNullException e) {
            log.error("Payment Creation Null Error");
            DataNullException response = new DataNullException(4102, "EF4102", "Payment or PaymentItem Data Null");
            return ResponseEntity.ok().body(response);
        } catch (DataRuntimeException e) {
            log.error("Payment Creation Runtime Error");
            DataRuntimeException response = new DataRuntimeException(4102, "EF4102", "Payment or PaymentItem Data Runtime Error");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            log.error("Payment Creation Error");
            Exception response = new Exception("Payment Creation Error", e);
            return ResponseEntity.ok().body(response);
        }
    }

    /*
    2023-08-10
    PaymentNo 조회후 삭제 - payment DB, paymentItem DB (status N으로 변경)
    */
    @PutMapping("/payment/type")
    public ResponseEntity<?> deletePayment(@RequestBody String paymentNo) throws Exception {
        try {
            paymentService.deletePayment(paymentNo);
            paymentService.deletePaymentItem(paymentNo);
            String response = new String("Payment, PaymentItem deleted successfully.");
            return ResponseEntity.ok().body(response);
        } catch (DataNullException e) {
            log.error("Payment Deletion Null Error");
            DataNullException response = new DataNullException(4102, "EF4102", "Payment, PaymentItem Data Null");
            return ResponseEntity.ok().body(response);
        } catch (DataRuntimeException e) {
            log.error("Payment Deletion Runtime Error");
            DataRuntimeException response = new DataRuntimeException(4102, "EF4102", "Payment, PaymentItem Data Runtime Error");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            log.error("Payment Deletion Error");
            Exception response = new Exception("Payment Deletion Error", e);
            return ResponseEntity.ok().body(response);
        }
    }

    /**
     * 월별 전체 통계 조회
     * @param paymentCreatedAt
     * @return
     */
    @GetMapping("/payment/admin/{month}/statistics")
    public ResponseEntity<Object> getSumByDate(
            @PathVariable("month") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime paymentCreatedAt) {
        try {
            log.debug("getSumByDate");
            PaymentStatisticDto result = paymentService.findSumByDate(paymentCreatedAt);
            return ResponseEntity.ok(result);
        } catch (DataRuntimeException e) {
            log.error("Load Data Runtime Exception");
            DataRuntimeException response = new DataRuntimeException(FAILED_LOAD_DATA);
            return ResponseEntity.ok(response);
        }
    }

    /**
     * 전체 회원 결제 내역 월별 조회
     * @param paymentCreatedAt
     * @return
     */
    @GetMapping("/payment/admin/{month}/detail")
    public ResponseEntity<Object> getAllPaymentListByDate(
            @PathVariable("month") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime paymentCreatedAt) {
        try {
            log.debug("getAllPaymentlistByDate");
            List<PaymentListDto> result = paymentService.findByDate(paymentCreatedAt);
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
     * 월별 회원별 결제 총 금액, 수량 조회
     * @param paymentCreatedAt
     * @return
     */
    @GetMapping("/payment/admin/{month}/total")
    public ResponseEntity<Object> getSumByDateAndUser(
            @PathVariable("month") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime paymentCreatedAt) {
        try {
            log.debug("getSumByDateAndUser");
            List<PaymentMonthlyTotalDto> result = paymentService.findSumByDateAndUser(paymentCreatedAt);
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

    /*
    * 마이페이지 월별 결제내역 조회
    * @param paymentCreatedAt, userNo
    * 유저별 결제 내역조회를 위해 월, 유저번호를 통한 조회
    * Payment error code 5000번대로 임의 설정
    * */
    @GetMapping("/payment/mypage/{month}/{userNo}/detail")
    public ResponseEntity<Object> getMySumByDate(
            @PathVariable("month") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime paymentCreatedAt,
            @PathVariable("userNo") String userNo){
        try {
            List<PaymentUserDto> result = paymentService.findMySumByDate(paymentCreatedAt, userNo);
            return ResponseEntity.ok(result);
        } catch (DataNullException e) {
            log.error("No payment error");
            DataNullException response = new DataNullException(5101, "EF5101", "Payment Data Null Error");
            return ResponseEntity.ok().body(response);
        } catch (DataRuntimeException e) {
            log.error("Payment Create Runtime Error");
            DataRuntimeException response = new DataRuntimeException(5101, "EF5101", "Payment Runtime Error");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            log.error("Payment Create Error");
            Exception response = new Exception("Payment not create", e);
            return ResponseEntity.ok().body(response);
        }
    }
}
