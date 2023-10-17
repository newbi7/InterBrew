package com.toyproject.internbrew_backend.payment.repository;

import com.toyproject.internbrew_backend.payment.entity.PaymentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentItemRepository extends JpaRepository<PaymentItem, String> {

}