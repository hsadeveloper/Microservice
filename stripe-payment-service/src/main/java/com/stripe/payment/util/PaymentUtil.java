package com.stripe.payment.util;

import com.stripe.payment.entity.StripePaymentEntity;
import com.stripe.payment.model.PaymentDetails;

public class PaymentUtil {
    public static PaymentDetails fromEntity(StripePaymentEntity entity){
         PaymentDetails paymentDetails = new PaymentDetails();
         paymentDetails.setId(entity.getId());
         paymentDetails.setAmount(entity.getAmount());
         paymentDetails.setCurrency(entity.getCurrency());
         paymentDetails.setCustomerEmail(entity.getCustomerEmail());
         paymentDetails.setStatus(entity.getStatus());
         paymentDetails.setCreatedAt(entity.getCreatedAt());
         paymentDetails.setFailureReason(entity.getFailureReason());
         return paymentDetails;
    }
}