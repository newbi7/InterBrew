//package com.toyproject.internbrew_backend.user.service;
//
//import com.toyproject.internbrew_backend.coffee.entity.Coffee;
//import com.toyproject.internbrew_backend.coffee.repository.CoffeeRepository;
//import com.toyproject.internbrew_backend.payment.entity.Payment;
//import com.toyproject.internbrew_backend.payment.entity.PaymentItem;
//import com.toyproject.internbrew_backend.payment.repository.PaymentItemRepository;
//import com.toyproject.internbrew_backend.payment.repository.PaymentRepository;
//import com.toyproject.internbrew_backend.user.dto.UserDto;
//import com.toyproject.internbrew_backend.user.entity.User;
//import com.toyproject.internbrew_backend.user.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.Random;
//
//@Component
//@Transactional
//@RequiredArgsConstructor
//public class DummyData implements CommandLineRunner {
//
//    private Logger log = LogManager.getLogger();
//    private final UserRepository userRepository;
//    private final PaymentRepository paymentRepository;
//    private final PaymentItemRepository paymentItemRepository;
//    private final CoffeeRepository coffeeRepository;
//    private final JdbcTemplate jdbcTemplate;
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        UserDto adminUserDto = new UserDto();
//        adminUserDto.setUserId("admin@admin.com");
//        adminUserDto.setUserType("Y");
//        adminUserDto.setUserName("admin");
//        adminUserDto.setUserPw("admin123!@");
//        adminUserDto.setUserCreateAt(LocalDateTime.now());
//
//        UserDto beomSuUserDto = new UserDto();
//        beomSuUserDto.setUserId("beomSu.Jeong@saltlux.com");
//        beomSuUserDto.setUserType("N");
//        beomSuUserDto.setUserName("정범수");
//        beomSuUserDto.setUserPw("qjatn123!@");
//        beomSuUserDto.setUserCreateAt(LocalDateTime.now());
//
//        User adminUser = User.on(adminUserDto);
//        User beomSuUser = User.on(beomSuUserDto);
//
//        try {
//            boolean userAdminOptional = userRepository.existsByUserId("admin@admin.com");
//
//            if (!userAdminOptional) {
//                userRepository.save(adminUser);
//            } else {
//                log.warn("Admin already exists");
//            }
//
//            boolean userBeomSuOptional = userRepository.existsByUserId("beomSu.Jeong@saltlux.com");
//
//            if (!userBeomSuOptional) {
//                userRepository.save(beomSuUser);
//            } else {
//                log.warn("BeomSu user already exists");
//            }
//
//            Optional<User> adminAdd = userRepository.findByUserId("admin@admin.com");
//
//            for (int i = 0; i < 3; i++) {
//                Payment payment = new Payment();
//                payment.setUser(adminAdd.get());
//                payment.setPaymentCreatedAt(LocalDateTime.now());
//                payment.setPaymentPrice(1000 * (i + 1));
//                payment.setPaymentTotalCount(i + 1);
//                payment.setPaymentUpdatedAt(LocalDateTime.now());
//                payment.setPaymentUserStatus("Y");
//
//                try {
//                    paymentRepository.save(payment);
//                } catch (Exception e) {
//                    log.warn("Skipping duplicate key: " + e.getMessage());
//                }
//            }
//
//            Optional<User> beomSuAdd = userRepository.findByUserId("beomSu.Jeong@saltlux.com");
//
//            for (int i = 0; i < 3; i++) {
//                Payment payment = new Payment();
//                payment.setUser(beomSuAdd.get());
//                payment.setPaymentCreatedAt(LocalDateTime.now());
//                payment.setPaymentPrice(1000 * (i + 1));
//                payment.setPaymentTotalCount(i + 1);
//                payment.setPaymentUpdatedAt(LocalDateTime.now());
//                payment.setPaymentUserStatus("Y");
//
//                try {
//                    paymentRepository.save(payment);
//                } catch (Exception e) {
//                    log.warn("Skipping duplicate key: " + e.getMessage());
//                }
//            }
//        } catch (Exception e) {
//            log.error("An error occurred: " + e.getMessage());
//        }
//
//        List<Payment> allPayments = paymentRepository.findAll();
//        Random random = new Random();
//        List<Coffee> allCoffees = coffeeRepository.findAll();
//        for (
//                int i = 0;
//                i < 10; i++) {
//            PaymentItem paymentItem = new PaymentItem();
//            Payment firstPay = allPayments.get(0);
//            Payment payments = getRandomItemFromList(allPayments);
//
//            Coffee randomCoffee = getRandomItemFromList(allCoffees);
//
//            paymentItem.setCoffee(randomCoffee);
//            paymentItem.setPayment(payments);
//            paymentItem.setPaymentItemCount(random.nextInt(3) + 1);
//            paymentItem.setPaymentItemCreatedAt(LocalDateTime.now());
//            paymentItem.setPaymentItemUserStatus("Y");
//
//            paymentItemRepository.save(paymentItem);
//        }
//
//    }
//
//    private <T> T getRandomItemFromList(List<T> list) {
//        int randomIndex = new Random().nextInt(list.size());
//        return list.get(randomIndex);
//    }
//}
