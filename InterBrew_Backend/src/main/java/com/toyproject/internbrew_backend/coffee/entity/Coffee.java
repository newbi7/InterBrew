package com.toyproject.internbrew_backend.coffee.entity;

import com.toyproject.internbrew_backend.coffee.dto.CoffeeDto;
import com.toyproject.internbrew_backend.payment.entity.PaymentItem;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter // 초기 데이터 삽입을 위해 추가 -> 추후 삭제 예정
@NoArgsConstructor // 추후 access level 설정 예정
//@DynamicInsert
//@DynamicUpdate
@ToString
@Table(name = "COFFEE_TB")
public class Coffee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_coffee_no")
    private int coffeeId;

    @Column(columnDefinition = "VARCHAR(50)")
    private String coffeeName;
    @Column(columnDefinition = "VARCHAR(1000)")
    private String coffeeInformation;
    private int coffeePrice;
    private int coffeeCount;
    private LocalDateTime coffeeCreatedAt;
    private LocalDateTime coffeeExpireDate;
    @Column(columnDefinition = "VARCHAR(500)")
    private String coffeeImage;
    @ColumnDefault("null")
    private LocalDateTime coffeeUpdatedAt;
    @ColumnDefault("null")
    private String coffeeUserCreate;
    @ColumnDefault("null")
    private String coffeeUserUpdate;
    @ColumnDefault("'Y'")
    @Column(columnDefinition = "VARCHAR(1)")
    private String coffeeStatus;

    @OneToMany(mappedBy = "coffee")
    private List<PaymentItem> paymentItems = new ArrayList<>();

    private Coffee (CoffeeDto dto) {
        this.coffeeId = dto.getCoffeeId();
        this.coffeeName = dto.getCoffeeName();
        this.coffeeInformation = dto.getCoffeeInformation();
        this.coffeePrice = dto.getCoffeePrice();
        this.coffeeCount = dto.getCoffeeCount();
        this.coffeeCreatedAt = LocalDateTime.parse(dto.getCoffeeCreatedAt());
        this.coffeeExpireDate = LocalDateTime.parse(dto.getCoffeeExpireDate());
        this.coffeeImage = dto.getCoffeeImage();
        this.coffeeUpdatedAt = dto.getCoffeeUpdatedAt();
        this.coffeeUserCreate = dto.getCoffeeUserCreate();
        this.coffeeUserUpdate = dto.getCoffeeUserUpdate();
        this.coffeeStatus = dto.getCoffeeStatus();
    }

    public static Coffee on(CoffeeDto dto) {
        return new Coffee(dto);
    }

}

