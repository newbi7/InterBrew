package com.toyproject.internbrew_backend.payment.repository;

import com.toyproject.internbrew_backend.payment.dto.PaymentDto;
import com.toyproject.internbrew_backend.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String>, PaymentRepositoryCustom {
    Payment findByPaymentNo(String paymentNo);



//    version 1. 전체 회원 결제 내역 월별 조회 querydsl 미적용
//    @Query("SELECT new com.toyproject.internbrew_backend.payment.dto.PaymentListDto(i, p, u, c) " +
//            "FROM PaymentItem i " +
//            "JOIN i.payment p " +
//            "JOIN p.user u " +
//            "JOIN i.coffee c " +
//            "WHERE FUNCTION('YEAR', i.paymentItemCreatedAt) = FUNCTION('YEAR', :paymentItemCreatedAt) " +
//            "AND FUNCTION('MONTH', i.paymentItemCreatedAt) = FUNCTION('MONTH', :paymetItemCreatedAt)")
//    List<PaymentListDto> findByDate(LocalDateTime paymentItemCreatedAt);



}