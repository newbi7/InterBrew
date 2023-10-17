package com.toyproject.internbrew_backend.payment.dto;

import com.toyproject.internbrew_backend.coffee.entity.Coffee;
import com.toyproject.internbrew_backend.payment.entity.Payment;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class PaymentItemDto {

    private String paymentItemId;
    private Payment payment;
    private Coffee coffee;
    private int paymentItemCount;
    private LocalDateTime paymentItemCreatedAt;
    private LocalDateTime paymentItemUpdatedAt;
    private String paymentItemUserCreate;
    private String paymentItemUserUpdate;
    private String paymentItemUserStatus;

    public PaymentItemDto() {

    }

    public PaymentItemDto(String paymentItemId, Payment payment,
                          Coffee coffee, int paymentItemCount,
                          LocalDateTime paymentItemCreatedAt, LocalDateTime paymentItemUpdatedAt,
                          String paymentItemUserCreate, String paymentItemUserUpdate,
                          String paymentItemUserStatus) {
        this.paymentItemId = paymentItemId;
        this.payment = payment;
        this.coffee = coffee;
        this.paymentItemCount = paymentItemCount;
        this.paymentItemCreatedAt = paymentItemCreatedAt;
        this.paymentItemUpdatedAt = paymentItemUpdatedAt;
        this.paymentItemUserCreate = paymentItemUserCreate;
        this.paymentItemUserUpdate = paymentItemUserUpdate;
        this.paymentItemUserStatus = paymentItemUserStatus;
    }


}
