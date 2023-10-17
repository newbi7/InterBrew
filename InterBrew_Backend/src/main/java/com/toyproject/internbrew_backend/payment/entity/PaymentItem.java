package com.toyproject.internbrew_backend.payment.entity;

import com.toyproject.internbrew_backend.coffee.entity.Coffee;
import com.toyproject.internbrew_backend.payment.dto.PaymentItemDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "PAYMENT_ITEM_TB")
public class PaymentItem {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "pk_payment_item_id", columnDefinition = "VARCHAR(50) NOT NULL")
    private String paymentItemId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "fk_payment_no", referencedColumnName = "pk_payment_no")
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "fk_coffee_no", referencedColumnName = "pk_coffee_no")
    private Coffee coffee;

    @Column(name = "payment_item_count")
    private int paymentItemCount;

    @Column(name = "payment_item_created_at", columnDefinition = "DATETIME DEFAULT NULL")
    private LocalDateTime paymentItemCreatedAt;

    @Column(name = "payment_item_updated_at", columnDefinition = "DATETIME DEFAULT NULL")
    private LocalDateTime paymentItemUpdatedAt;

    @Column(name = "payment_item_user_create", columnDefinition = "VARCHAR(50) DEFAULT NULL")
    private String paymentItemUserCreate;

    @Column(name = "payment_item_user_update", columnDefinition = "VARCHAR(50) DEFAULT NULL")
    private String paymentItemUserUpdate;

    @Column(name = "payment_item_user_status", columnDefinition = "VARCHAR(1) DEFAULT 'Y'")
    private String paymentItemUserStatus = "Y";


    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
    }


    //////////// 더미데이터용 삭제 예정 /////////////////
    public void setPaymentItemCount(int paymentItemCount) {
        this.paymentItemCount = paymentItemCount;
    }

    public void setPaymentItemCreatedAt(LocalDateTime paymentItemCreatedAt) {
        this.paymentItemCreatedAt = paymentItemCreatedAt;
    }

    public void setPaymentItemUserStatus(String paymentItemUserStatus) {
        this.paymentItemUserStatus = paymentItemUserStatus;
    }

    //////////////////////////////////////////////////


    private PaymentItem(PaymentItemDto dto) {
        this.paymentItemId = dto.getPaymentItemId();
        this.payment = dto.getPayment();
        this.coffee = dto.getCoffee();
        this.paymentItemCount = dto.getPaymentItemCount();
        this.paymentItemCreatedAt = dto.getPaymentItemCreatedAt();
        this.paymentItemUpdatedAt = dto.getPaymentItemUpdatedAt();
        this.paymentItemUserCreate = dto.getPaymentItemUserCreate();
        this.paymentItemUserUpdate = dto.getPaymentItemUserUpdate();
        this.paymentItemUserStatus = dto.getPaymentItemUserStatus();
    }

    public static PaymentItem on(PaymentItemDto dto) {
        return new PaymentItem(dto);
    }
}
