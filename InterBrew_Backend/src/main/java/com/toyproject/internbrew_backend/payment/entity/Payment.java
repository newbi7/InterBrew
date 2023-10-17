package com.toyproject.internbrew_backend.payment.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.toyproject.internbrew_backend.payment.dto.PaymentDto;
import com.toyproject.internbrew_backend.user.entity.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "PAYMENT_TB")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payment {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "pk_payment_no", columnDefinition = "VARCHAR(50) NOT NULL")
    private String paymentNo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "fk_user_no", referencedColumnName = "pk_user_no")
    private User user;

    @OneToMany(mappedBy = "payment")
    private List<PaymentItem> paymentItems = new ArrayList<>();

    @Column(name = "payment_created_at", columnDefinition = "DATETIME")
    private LocalDateTime paymentCreatedAt;

    @Column(name = "payment_price", columnDefinition = "INT")
    private int paymentPrice;

    @Column(name = "payment_total_count", columnDefinition = "INT")
    private int paymentTotalCount;

    @Column(name = "payment_updated_at", columnDefinition = "DATETIME DEFAULT NULL")
    private LocalDateTime paymentUpdatedAt;

    @Column(name = "payment_user_create", columnDefinition = "VARCHAR(50) DEFAULT NULL")
    private String paymentUserCreate;

    @Column(name = "payment_user_update", columnDefinition = "VARCHAR(50) DEFAULT NULL")
    private String paymentUserUpdate;

    @Column(name = "payment_user_status", columnDefinition = "VARCHAR(1) DEFAULT 'Y'")
    private String paymentUserStatus = "Y";

    public void setUser(User user) {
        this.user = user;
    }

    /////////////// 더미데이터용 지울 예정////////////////////
    public void setPaymentCreatedAt(LocalDateTime paymentCreatedAt) {
        this.paymentCreatedAt = paymentCreatedAt;
    }

    public void setPaymentPrice(int paymentPrice) {
        this.paymentPrice = paymentPrice;
    }

    public void setPaymentTotalCount(int paymentTotalCount) {
        this.paymentTotalCount = paymentTotalCount;
    }

    public void setPaymentUpdatedAt(LocalDateTime paymentUpdatedAt) {
        this.paymentUpdatedAt = paymentUpdatedAt;
    }

    public void setPaymentUserStatus(String paymentUserStatus) {
        this.paymentUserStatus = paymentUserStatus;
    }

    //////////////////////////////////////////

    private Payment (PaymentDto dto) {
        this.paymentNo = dto.getPaymentNo();
        this.user = dto.getUser();
        this.paymentCreatedAt = dto.getPaymentCreatedAt();
        this.paymentPrice = dto.getPaymentPrice();
        this.paymentTotalCount = dto.getPaymentTotalCount();
        this.paymentUpdatedAt = dto.getPaymentUpdatedAt();
        this.paymentUserCreate = dto.getPaymentUserCreate();
        this.paymentUserUpdate = dto.getPaymentUserUpdate();
        this.paymentUserStatus = dto.getPaymentUserStatus();
    }

    public static Payment on(PaymentDto dto) {
        return new Payment(dto);
    }
}
