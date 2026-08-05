package com.stripe.payment.repository;

import com.stripe.payment.entity.StripePaymentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StripePaymentRepository extends JpaRepository<StripePaymentEntity, Long> {
    Optional<StripePaymentEntity> findByPaymentIntentId(String paymentIntentId);

    // Or update status directly using JPQL
    @Transactional
    @Modifying
    @Query("UPDATE StripePaymentEntity p SET p.status = :status, p.failureReason = :failureReason WHERE p.paymentIntentId = :intentId")
    int updateStatusByIntentId(
            @Param("intentId") String intentId,
            @Param("status") String status,
            @Param("failureReason") String failureReason
    );
}
