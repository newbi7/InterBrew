package com.toyproject.internbrew_backend.payment.dto;

import com.toyproject.internbrew_backend.user.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class PaymentUserDto {

    private String coffeeName;
    private LocalDateTime paymentCreatedAt;
    private int paymentPrice;
    private int paymentTotalCount;

    public PaymentUserDto() {
    }

    public PaymentUserDto(String coffeeName, LocalDateTime paymentCreatedAt, int paymentPrice, int paymentTotalCount) {
        this.coffeeName = coffeeName;
        this.paymentCreatedAt = paymentCreatedAt;
        this.paymentPrice = paymentPrice;
        this.paymentTotalCount = paymentTotalCount;
    }


}
