package com.toyproject.internbrew_backend.coffee.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.toyproject.internbrew_backend.coffee.entity.Coffee;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoffeeDto {

    private int coffeeId;
    private String coffeeName;
    private String coffeeInformation;
    private int coffeePrice;
    private int coffeeCount;
    private String coffeeCreatedAt;
    private String coffeeExpireDate;
    private String coffeeImage;
    private LocalDateTime coffeeUpdatedAt;
    private String coffeeUserCreate;
    private String coffeeUserUpdate;
    private String coffeeStatus;

    public CoffeeDto(Coffee coffee) {
        this.coffeeId = coffee.getCoffeeId();
        this.coffeeName = coffee.getCoffeeName();
        this.coffeeInformation = coffee.getCoffeeInformation();
        this.coffeePrice = coffee.getCoffeePrice();
        this.coffeeCount = coffee.getCoffeeCount();
        this.coffeeCreatedAt = coffee.getCoffeeCreatedAt().toString();
        this.coffeeExpireDate = coffee.getCoffeeExpireDate().toString();
        this.coffeeImage = coffee.getCoffeeImage();
        this.coffeeUpdatedAt = coffee.getCoffeeUpdatedAt();
        this.coffeeUserCreate = coffee.getCoffeeUserCreate();
        this.coffeeUserUpdate = coffee.getCoffeeUserUpdate();
        this.coffeeStatus = coffee.getCoffeeStatus();
    }
}
