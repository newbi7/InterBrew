package com.toyproject.internbrew_backend.payment.repository;

import com.toyproject.internbrew_backend.payment.dto.PaymentListDto;
import com.toyproject.internbrew_backend.payment.dto.PaymentMonthlyTotalDto;
import com.toyproject.internbrew_backend.payment.dto.PaymentStatisticDto;
import com.toyproject.internbrew_backend.payment.dto.PaymentUserDto;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepositoryCustom {

    List<PaymentListDto> findByDate(LocalDateTime paymentItemCreatedAt);

    List<PaymentMonthlyTotalDto> findSumByDateAndUser(LocalDateTime paymentCreatedAt);

    PaymentStatisticDto findSumByDate(LocalDateTime paymentCreatedAt);

    List<PaymentUserDto> findMySumByDate(LocalDateTime paymentCreatedAt, String userNo);
}
