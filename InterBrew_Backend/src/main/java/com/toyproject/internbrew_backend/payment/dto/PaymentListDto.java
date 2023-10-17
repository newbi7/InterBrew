package com.toyproject.internbrew_backend.payment.dto;

import com.toyproject.internbrew_backend.coffee.entity.Coffee;
import com.toyproject.internbrew_backend.payment.entity.Payment;
import com.toyproject.internbrew_backend.payment.entity.PaymentItem;
import com.toyproject.internbrew_backend.user.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PaymentListDto {

    private String userId;
    private int paymentPrice;
    private int paymentTotalCount;
    private String coffeeName;
    private int coffeePrice;
    private LocalDateTime paymentCreatedAt;
    private int paymentItemCount;

    public PaymentListDto(PaymentItem paymentItem, Payment payment, User user, Coffee coffee){
        this.userId = user.getUserId();
        this.paymentPrice = payment.getPaymentPrice();
        this.paymentTotalCount = payment.getPaymentTotalCount();
        this.coffeeName = paymentItem.getCoffee().getCoffeeName();
        this.coffeePrice = paymentItem.getCoffee().getCoffeePrice();
        this.paymentCreatedAt = payment.getPaymentCreatedAt();
        this.paymentItemCount = paymentItem.getPaymentItemCount();
    }


}
