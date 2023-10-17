//package com.toyproject.internbrew_backend.coffee.service;
//
//import com.toyproject.internbrew_backend.coffee.entity.Coffee;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.PostConstruct;
//import javax.persistence.EntityManager;
//import java.time.LocalDateTime;
//
//// 데이터베이스 더미 데이터 삽입용 (삭제 가능 부분)
//@Component
//@RequiredArgsConstructor
//public class CoffeeInitDb {
//
//    private final InitService initService;
//
//    @PostConstruct
//    public void init(){
//        initService.dbInit();
//    }
//
//    @Service
//    @Transactional
//    @RequiredArgsConstructor
//    static class InitService{
//
//        private final EntityManager em;
//
//        public void dbInit(){
//            Coffee coffee1 = new Coffee();
//            coffee1.setCoffeeName("decaffein");
//            coffee1.setCoffeeInformation("카페인이 97%이상 제거된 디카페인 음");
//            coffee1.setCoffeePrice(500);
//            coffee1.setCoffeeCount(10);
//            coffee1.setCoffeeCreatedAt(LocalDateTime.of(2023, 8, 10, 0, 0));
//            coffee1.setCoffeeExpireDate(LocalDateTime.of(2023, 9, 10, 0, 0));
//            coffee1.setCoffeeImage("http://localhost/static/images/coffee/decaffein.png");
//            coffee1.setCoffeeStatus("Y");
//            em.persist(coffee1);
//
//            Coffee coffee2 = new Coffee();
//            coffee2.setCoffeeName("forte");
//            coffee2.setCoffeeInformation("미디엄 로스팅된 맥아향과 과일향");
//            coffee2.setCoffeePrice(500);
//            coffee2.setCoffeeCount(20);
//            coffee2.setCoffeeCreatedAt(LocalDateTime.of(2023, 7, 10, 0, 0));
//            coffee2.setCoffeeExpireDate(LocalDateTime.of(2023, 8, 10, 0, 0));
//            coffee2.setCoffeeImage("http://localhost/static/images/coffee/forte.png");
//            coffee2.setCoffeeStatus("Y");
//            em.persist(coffee2);
//
//            Coffee coffee3 = new Coffee();
//            coffee3.setCoffeeName("brazil");
//            coffee3.setCoffeeInformation("부드러운 꿀과 고소한 곡물향");
//            coffee3.setCoffeePrice(500);
//            coffee3.setCoffeeCount(30);
//            coffee3.setCoffeeCreatedAt(LocalDateTime.of(2023, 7, 10, 0, 0));
//            coffee3.setCoffeeExpireDate(LocalDateTime.of(2023, 9, 10, 0, 0));
//            coffee3.setCoffeeImage("image");
//            coffee3.setCoffeeStatus("Y");
//            em.persist(coffee3);
//        }
//    }
//}
