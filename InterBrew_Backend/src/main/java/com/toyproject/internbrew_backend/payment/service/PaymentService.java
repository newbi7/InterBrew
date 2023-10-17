package com.toyproject.internbrew_backend.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyproject.internbrew_backend.coffee.entity.Coffee;
import com.toyproject.internbrew_backend.coffee.repository.CoffeeRepository;
import com.toyproject.internbrew_backend.payment.dto.*;
import com.toyproject.internbrew_backend.payment.entity.Payment;
import com.toyproject.internbrew_backend.payment.entity.PaymentItem;
import com.toyproject.internbrew_backend.payment.repository.PaymentItemRepository;
import com.toyproject.internbrew_backend.payment.repository.PaymentRepository;
import com.toyproject.internbrew_backend.user.entity.User;
import com.toyproject.internbrew_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final RedisTemplate<String, Object> redisTemplate;

    private final PaymentRepository paymentRepository;

    private final PaymentItemRepository paymentItemRepository;

    private final UserRepository userRepository;

    private final CoffeeRepository coffeeRepository;

    /*
    2023-08-09
    paymentNo을 key값으로 payment에 redis저장
    */
    public void createPaymentRedis(Object redis) {
        String order = "order:";
        redisTemplate.opsForValue().set(order, redis);
    }

    /*
    2023-08-09
    redis에 저장된 값을 paymentDB에 저장
    */
    public String createPayment(Object orderDetail) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String orderDetailJson = objectMapper.writeValueAsString(orderDetail);
            Map<String, Object> orderDetailMap = objectMapper.readValue(orderDetailJson, Map.class);

            String userId = orderDetailMap.get("userId").toString();
            int paymentPrice = Integer.parseInt(String.valueOf(orderDetailMap.get("paymentPrice")));
            int paymentTotalPrice = Integer.parseInt(String.valueOf(orderDetailMap.get("paymentTotalCount")));
            User payUser = userRepository.findByUserId(userId).get();

            PaymentDto orderDetailPaymentDto = new PaymentDto();
            orderDetailPaymentDto.setPaymentPrice(paymentPrice);
            orderDetailPaymentDto.setPaymentUserCreate(userId);
            orderDetailPaymentDto.setPaymentCreatedAt(LocalDateTime.now());
            orderDetailPaymentDto.setPaymentUpdatedAt(LocalDateTime.now());
            orderDetailPaymentDto.setPaymentUserStatus("Y");
            orderDetailPaymentDto.setPaymentTotalCount(paymentTotalPrice);
            orderDetailPaymentDto.setUser(payUser);

            Payment orderDetailPayment = Payment.on(orderDetailPaymentDto);
            Payment savedPayment = paymentRepository.save(orderDetailPayment);
            return savedPayment.getPaymentNo();
        } catch (Exception e){
            log.error("Payment Creation Error");
            throw new RuntimeException("Payment Creation Error", e);
        }
    }

    /*
    2023-08-27
    redis에 저장된 값을 paymentitemDB에저장
    */
    public void createPaymentId(Object orderDetail, String paymentNo) throws Exception {
        try {
            Payment orderPayment = paymentRepository.findByPaymentNo(paymentNo);

            ObjectMapper objectMapper = new ObjectMapper();
            String orderDetailJson = objectMapper.writeValueAsString(orderDetail);
            Map<String, Object> orderDetailMap = objectMapper.readValue(orderDetailJson, Map.class);

            String userId = orderDetailMap.get("userId").toString();
            List<Map<String, Object>> paymentList = (List<Map<String, Object>>) orderDetailMap.get("payment");

            for (Map<String, Object> paymentItem : paymentList) {
                int coffeeId = Integer.parseInt(paymentItem.get("coffeeNo").toString());
                int paymentItemCount = Integer.parseInt(paymentItem.get("quantity").toString());
                Coffee orderCoffeeDto = coffeeRepository.findByCoffeeId(coffeeId);

                try {
                    int remainingCoffeeCount = orderCoffeeDto.getCoffeeCount() - Integer.parseInt(paymentItem.get("quantity").toString());
                    if (remainingCoffeeCount < 0) {
                        throw new Exception("Coffee Stock Not Enough");
                    }
                    orderCoffeeDto.setCoffeeCount(remainingCoffeeCount);
                    coffeeRepository.save(orderCoffeeDto);
                } catch (Exception e) {
                    throw new Exception("An error occurred while processing the Coffee order");
                }

                PaymentItemDto orderDetailPaymentItemDto = new PaymentItemDto();
                orderDetailPaymentItemDto.setPaymentItemUserCreate(userId);
                orderDetailPaymentItemDto.setPaymentItemCreatedAt(LocalDateTime.now());
                orderDetailPaymentItemDto.setPaymentItemUpdatedAt(LocalDateTime.now());
                orderDetailPaymentItemDto.setPaymentItemUserStatus("Y");
                orderDetailPaymentItemDto.setPaymentItemCount(paymentItemCount);
                orderDetailPaymentItemDto.setPayment(orderPayment);
                orderDetailPaymentItemDto.setCoffee(orderCoffeeDto);

                PaymentItem orderDetailPaymentItem = PaymentItem.on(orderDetailPaymentItemDto);
                paymentItemRepository.save(orderDetailPaymentItem);
            }
        } catch (Exception e) {
            log.error("PaymentItem Creation Error");
            throw e;
        }
    }

    /*
    2023-08-10
    paymentNo 조회후 userStatus 를 "N"으로 설정(삭제처리) 수정필요!!!!
    */
    public void deletePayment(String paymentNo) {
        Payment payment = paymentRepository.findByPaymentNo(paymentNo);


//        if (paymentDto != null) {
//            paymentDto.setPaymentUserStatus("N");
//            Payment entity = Payment.on(paymentDto);
//            paymentRepository.save(entity);
//        } else {
//            throw new DataNullException(4102, "EF4102", "Payment not found");
//        }
    }


    public void deletePaymentItem(String paymentNo) {
    }

    /**
     * 전체 회원 주문 내역 월별 조회
     * @param paymentCreatedAt
     * @return
     */
    public List<PaymentListDto> findByDate(LocalDateTime paymentCreatedAt) {
        return paymentRepository.findByDate(paymentCreatedAt);
    }

    /**
     * 월별 회원별 결제 총 금액, 수량 조회
     * @param paymentCreatedAt
     * @return
     */
    public List<PaymentMonthlyTotalDto> findSumByDateAndUser(LocalDateTime paymentCreatedAt) {
        return paymentRepository.findSumByDateAndUser(paymentCreatedAt);
    }

    /**
     * 월별 전체 통계 조회
     * @param paymentCreatedAt
     * @return
     */
    public PaymentStatisticDto findSumByDate(LocalDateTime paymentCreatedAt) {
        return paymentRepository.findSumByDate(paymentCreatedAt);
    }

    public List<PaymentUserDto> findMySumByDate(LocalDateTime paymentCreatedAt, String userNo) {
        return paymentRepository.findMySumByDate(paymentCreatedAt, userNo);
    }

//    public PaymentStatisticDto findMySumByDate(LocalDateTime paymentCreatedAt, String userNo) {
//        return paymentRepository.findMySumByData(paymentCreatedAt);
//    }
}
