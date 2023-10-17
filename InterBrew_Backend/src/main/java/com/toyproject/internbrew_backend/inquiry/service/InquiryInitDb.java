//package com.toyproject.internbrew_backend.inquiry.service;
//
//import com.toyproject.internbrew_backend.inquiry.entity.Inquiry;
//import com.toyproject.internbrew_backend.user.dto.UserDto;
//import com.toyproject.internbrew_backend.user.entity.User;
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
//public class InquiryInitDb {
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
//
//            Inquiry inquiry1 = new Inquiry();
//            inquiry1.setInquiryContents("보리차 주세요");
//            inquiry1.setInquiryCreatedAt(LocalDateTime.of(2023, 8, 10, 0, 0));
//            inquiry1.setInquiryUpdatedAt(LocalDateTime.of(2023, 8, 11, 0, 0));
//            inquiry1.setInquiryUserStatus("Y");
//
//            UserDto userDto = new UserDto();
//            userDto.setUserNo("f0d0e6bb-6a0e-4cc3-9f94-891ecf6218ab");
//            User user = User.on(userDto);
//            user = em.merge(user);
//            inquiry1.setUser(user);
//
//            em.persist(inquiry1);
//        }
//
//    }
//}
