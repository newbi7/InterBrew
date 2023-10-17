package com.toyproject.internbrew_backend.payment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PaymentMonthlyTotalDto {

    public String userId;
    public int paymentTotalPrice;
    public int paymentTotalCount;
    public LocalDateTime paymentCreatedAt;

    public PaymentMonthlyTotalDto(String userId, int paymentTotalPrice, int paymentTotalCount, LocalDateTime paymentCreatedAt) {
        this.userId = userId;
        this.paymentTotalPrice = paymentTotalPrice;
        this.paymentTotalCount = paymentTotalCount;
        this.paymentCreatedAt = paymentCreatedAt;
    }

}
