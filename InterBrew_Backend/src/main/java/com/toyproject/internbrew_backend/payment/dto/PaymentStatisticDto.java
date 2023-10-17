package com.toyproject.internbrew_backend.payment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PaymentStatisticDto {

    private int paymentMonthlyTotalPrice;
    private int paymentMonthlyTotalCount;


    public PaymentStatisticDto(int paymentMonthlyTotalPrice, int paymentMonthlyTotalCount) {
        this.paymentMonthlyTotalPrice = paymentMonthlyTotalPrice;
        this.paymentMonthlyTotalCount = paymentMonthlyTotalCount;
    }
}
