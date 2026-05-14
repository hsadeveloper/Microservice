package com.stripe.payment.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.stripe.payment.entity.StripePaymentEntity;
import com.stripe.payment.repository.StripePaymentRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class StripePaymentService {
    private final StripePaymentRepository repo;

    public StripePaymentService(StripePaymentRepository repo) {
        this.repo = repo;
    }

    public void savePayment(String paymentIntentId, Map<String, Object> data) {
    	Long amount = ((Number) data.get("amount")).longValue(); // in cents
        StripePaymentEntity payment = StripePaymentEntity.builder()
                .paymentIntentId(paymentIntentId)
                .amount(amount)
                .currency((String)data.get("currency"))
                .status((String)data.get("status"))
                .customerEmail((String)data.get("email"))
                .build();
        repo.save(payment);
        log.info("💾 Saved payment intent {} with status {}", paymentIntentId, (String)data.get("status"));
    }

    public void updatePaymentStatus(String paymentIntentId, String newStatus) {
        StripePaymentEntity payment = repo.findByPaymentIntentId(paymentIntentId);
        if (payment != null) {
            payment.setStatus(newStatus);
            repo.save(payment);
            log.info("🔄 Updated payment {} status to {}", paymentIntentId, newStatus);
        }
    }
}