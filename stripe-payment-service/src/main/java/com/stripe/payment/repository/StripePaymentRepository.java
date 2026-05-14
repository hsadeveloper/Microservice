package com.stripe.payment.repository;

import com.stripe.payment.entity.StripePaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StripePaymentRepository extends JpaRepository<StripePaymentEntity, Long> {
    StripePaymentEntity findByPaymentIntentId(String paymentIntentId);
}
