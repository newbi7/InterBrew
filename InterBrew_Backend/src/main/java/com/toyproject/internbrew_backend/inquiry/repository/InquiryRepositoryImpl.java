package com.toyproject.internbrew_backend.inquiry.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toyproject.internbrew_backend.inquiry.dto.InquiryDto;
import com.toyproject.internbrew_backend.inquiry.dto.InquiryListDto;
import com.toyproject.internbrew_backend.inquiry.entity.Inquiry;
import com.toyproject.internbrew_backend.inquiry.entity.QInquiry;
import com.toyproject.internbrew_backend.user.dto.UserDto;
import com.toyproject.internbrew_backend.user.entity.QUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public class InquiryRepositoryImpl implements InquiryRepositoryCustom {

    private Logger log = LogManager.getLogger();

    private final JPAQueryFactory queryFactory;

    public InquiryRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<InquiryListDto> findAllInquiryWithUserId() {

        QInquiry inquiry = QInquiry.inquiry;
        QUser user = QUser.user;

        List<InquiryListDto> results = queryFactory
                .select(Projections.constructor(InquiryListDto.class, inquiry, user))
                .from(inquiry)
                .leftJoin(inquiry.user, user)
                .orderBy(inquiry.inquiryCreatedAt.desc())
                .fetch();

        return results;
    }

    @Override
    public List<InquiryListDto> findByStatus(String status) {

        QInquiry inquiry = QInquiry.inquiry;
        QUser user = QUser.user;

        List<InquiryListDto> results = queryFactory
                .select(Projections.constructor(InquiryListDto.class, inquiry, user))
                .from(inquiry)
                .leftJoin(inquiry.user, user)
                .where(inquiry.inquiryUserStatus.eq(status))
                .orderBy(inquiry.inquiryCreatedAt.desc())
                .fetch();

        return results;
    }

    @Override
    public Long findTheNumberOfUnsolved() {

        QInquiry inquiry = QInquiry.inquiry;

        Long count = queryFactory
                .select(inquiry.count())
                .from(inquiry)
                .where(inquiry.inquiryUserStatus.eq("Y"))
                .fetchOne();

        return count;
    }

}
