package com.stripe.payment.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.payment.enums.PaymentStatus;
import com.stripe.payment.exception.PaymentNotFoundException;
import com.stripe.payment.model.PaymentDetails;
import com.stripe.payment.model.PaymentRequest;
import com.stripe.payment.util.PaymentUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.stripe.payment.entity.StripePaymentEntity;
import com.stripe.payment.repository.StripePaymentRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class PaymentService {
    private final StripePaymentRepository paymentRepository;
    private final StripePaymentService makeStripePayment;

    public PaymentService(StripePaymentRepository paymentRepository, StripePaymentService makeStripePayment) {
        this.paymentRepository = paymentRepository;
        this.makeStripePayment = makeStripePayment;
    }

    @Transactional
    public void savePayment(PaymentIntent paymentIntent, PaymentRequest paymentRequest) throws StripeException {

        StripePaymentEntity stripePaymentEntity = new StripePaymentEntity();
        stripePaymentEntity.setPaymentIntentId(paymentIntent.getId());
        stripePaymentEntity.setStatus(PaymentStatus.FAILED.name());
        stripePaymentEntity.setPaymentIntentId(paymentIntent.getId());
        stripePaymentEntity.setAmount(paymentRequest.getAmount());
        stripePaymentEntity.setCurrency(paymentIntent.getCurrency());
        stripePaymentEntity.setStatus(paymentIntent.getStatus());
        stripePaymentEntity .setCustomerEmail(paymentRequest.getReceiptEmail());

        paymentRepository.save(stripePaymentEntity);
        //log.info("💾 Saved payment intent {} with status {}", paymentIntentId, (String)data.get("status"));
        System.out.println("💾 Saved payment intent {} with status {}"+ paymentIntent.getId()+" : "+paymentIntent.getStatus());
    }

    @Transactional
    public void updatePaymentStatus(String paymentIntentId, String newStatus, String failureReason) {
        Optional<StripePaymentEntity> stripePaymentEntity = paymentRepository.findByPaymentIntentId(paymentIntentId);
        if (stripePaymentEntity.isPresent()) {
            StripePaymentEntity payment = stripePaymentEntity.get();
            payment.setStatus(newStatus);
            payment.setFailureReason(failureReason);
            paymentRepository.save(payment);
            //log.info("🔄 Updated payment {} status to {}", paymentIntentId, newStatus);
            System.out.println("🔄 Updated payment {} status to {}"+ paymentIntentId + " : "+ newStatus);
        }
    }

    public PaymentDetails getPaymentDetails(long id) throws PaymentNotFoundException{
       Optional<StripePaymentEntity> stripePaymentEntity = paymentRepository.findById(id);
       if (stripePaymentEntity.isPresent()){
           return PaymentUtil.fromEntity(stripePaymentEntity.get());
       }
       throw new PaymentNotFoundException("Invalid PaymentId..");
    }

    public List<PaymentDetails> getAllPayments(){
        return paymentRepository.findAll().stream().map(PaymentUtil::fromEntity).toList();
    }

    public List<PaymentDetails> getAllPaymentsByStatus(String status){
        return paymentRepository.findAll()
                .stream()
                .filter(p -> p.getStatus() != null && p.getStatus().equalsIgnoreCase(status)).map(PaymentUtil::fromEntity)
                .collect(Collectors.toList());
    }
}