package com.toyproject.internbrew_backend.payment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toyproject.internbrew_backend.coffee.entity.QCoffee;
import com.toyproject.internbrew_backend.payment.dto.PaymentListDto;

import com.toyproject.internbrew_backend.payment.dto.PaymentMonthlyTotalDto;
import com.toyproject.internbrew_backend.payment.dto.PaymentStatisticDto;
import com.toyproject.internbrew_backend.payment.dto.PaymentUserDto;
import com.toyproject.internbrew_backend.payment.entity.QPayment;
import com.toyproject.internbrew_backend.payment.entity.QPaymentItem;
import com.toyproject.internbrew_backend.user.entity.QUser;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class PaymentRepositoryImpl implements PaymentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PaymentRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    /**
     * version2. 전체 회원 결제 내역 월별 조회 querydsl적용
     * @param paymentItemCreatedAt
     * @return
     */
    @Override
    public List<PaymentListDto> findByDate(LocalDateTime paymentItemCreatedAt) {

        QPaymentItem paymentItem = QPaymentItem.paymentItem;
        QPayment payment = QPayment.payment;
        QUser user = QUser.user;
        QCoffee coffee = QCoffee.coffee;

        return queryFactory
                .select(Projections.constructor(PaymentListDto.class, paymentItem, payment, user, coffee))
                .from(paymentItem)
                .leftJoin(paymentItem.coffee, coffee)
                .leftJoin(paymentItem.payment, payment)
                .leftJoin(payment.user, user)
                .where(paymentItem.paymentItemCreatedAt.year().eq(paymentItemCreatedAt.getYear())
                        .and(paymentItem.paymentItemCreatedAt.month().eq(paymentItemCreatedAt.getMonthValue())))
                .orderBy(paymentItem.paymentItemCreatedAt.desc(), user.userId.asc(), coffee.coffeeName.asc())
                .fetch();
    }

    /**
     * 관리자 월별 회원별 총 결제 금액, 결제 수량 조회
     * @param paymentCreatedAt
     * @return
     */
    @Override
    public List<PaymentMonthlyTotalDto> findSumByDateAndUser(LocalDateTime paymentCreatedAt) {

        QPayment payment = QPayment.payment;
        QUser user = QUser.user;

        return queryFactory
                .select(Projections.constructor(PaymentMonthlyTotalDto.class,
                        payment.user.userId,
                        payment.paymentPrice.sum(),
                        payment.paymentTotalCount.sum(),
                        payment.paymentCreatedAt
                ))
                .from(payment)
                .leftJoin(payment.user, user)
                .where(payment.paymentCreatedAt.year().eq(paymentCreatedAt.getYear())
                        .and(payment.paymentCreatedAt.month().eq(paymentCreatedAt.getMonthValue())))
                .groupBy(payment.user.userId)
                .fetch();
    }

    /**
     * 월별 전체 통계 조회
     * @param paymentCreatedAt
     * @return
     */
    @Override
    public PaymentStatisticDto findSumByDate(LocalDateTime paymentCreatedAt) {

        QPayment payment = QPayment.payment;

        return queryFactory
                .select(Projections.constructor(PaymentStatisticDto.class,
                        payment.paymentPrice.sum(),
                        payment.paymentTotalCount.sum()
                ))
                .from(payment)
                .where(payment.paymentCreatedAt.year().eq(paymentCreatedAt.getYear())
                        .and(payment.paymentCreatedAt.month().eq(paymentCreatedAt.getMonthValue())))
                .fetchOne();
    }

    /*
    유저별 월별 결제 내역 조회
    @Param paymentCreatedAt, userNo
    */
    @Override
    public List<PaymentUserDto> findMySumByDate(LocalDateTime paymentCreatedAt, String userNo) {

        QPaymentItem paymentItem = QPaymentItem.paymentItem;
        QPayment payment = QPayment.payment;
        QUser user = QUser.user;
        QCoffee coffee = QCoffee.coffee;

        return queryFactory
                .select(Projections.constructor(PaymentUserDto.class,
                        coffee.coffeeName,
                        payment.paymentCreatedAt,
                        payment.paymentPrice,
                        payment.paymentTotalCount))
                .from(payment)
                .leftJoin(payment.paymentItems, paymentItem)
                .leftJoin(paymentItem.coffee, coffee)
                .where(payment.paymentCreatedAt.year().eq(paymentCreatedAt.getYear())
                        .and(payment.paymentCreatedAt.month().eq(paymentCreatedAt.getMonthValue()))
                        .and(payment.user.userNo.eq(userNo)))
                .fetch();
    }


}
