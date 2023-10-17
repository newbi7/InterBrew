package com.toyproject.internbrew_backend.payment.dto;

import com.toyproject.internbrew_backend.user.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class PaymentDto {

    private String paymentNo;
    private User user;
    private LocalDateTime paymentCreatedAt;
    private int paymentPrice;
    private int paymentTotalCount;
    private LocalDateTime paymentUpdatedAt;
    private String paymentUserCreate;
    private String paymentUserUpdate;
    private String paymentUserStatus;

    public PaymentDto() {

    }
    public PaymentDto(String paymentNo, User user, LocalDateTime paymentCreatedAt, int paymentPrice, int paymentTotalCount, LocalDateTime paymentUpdatedAt, String paymentUserCreate, String paymentUserUpdate, String paymentUserStatus) {
        this.paymentNo = paymentNo;
        this.user = user;
        this.paymentCreatedAt = paymentCreatedAt;
        this.paymentPrice = paymentPrice;
        this.paymentTotalCount = paymentTotalCount;
        this.paymentUpdatedAt = paymentUpdatedAt;
        this.paymentUserCreate = paymentUserCreate;
        this.paymentUserUpdate = paymentUserUpdate;
        this.paymentUserStatus = paymentUserStatus;
    }


}
