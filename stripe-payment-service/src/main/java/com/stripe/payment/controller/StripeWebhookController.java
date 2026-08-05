package com.stripe.payment.controller;

import com.stripe.exception.EventDataObjectDeserializationException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import com.stripe.payment.enums.PaymentStatus;
import com.stripe.payment.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/stripe")
public class StripeWebhookController {

    @Value("${stripe.webhook-secret}")
    private String endpointSecret;

    private final PaymentService paymentService;

    public StripeWebhookController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeEvent(HttpServletRequest request,
                                    @RequestHeader("Stripe-Signature") String sigHeader) {
        String payload;
        try (InputStream inputStream = request.getInputStream()) {
            payload = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("payload response : "+payload);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to read payload");
        }
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
            System.out.println("Received Stripe Event Type: {}"+ event.getType());
        } catch (SignatureVerificationException e) {
            //log.error("⚠️ Webhook signature verification failed: {}", e.getMessage());
            System.out.println("⚠️ Webhook signature verification failed: {}"+ e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }

        // Handle event type
        switch (event.getType()) {
            case "payment_intent.succeeded" -> {
                var deserializer = event.getDataObjectDeserializer();

                // 1. Try safe deserialization first, fallback to unsafe if API version mismatches
                PaymentIntent intent = (PaymentIntent) deserializer.getObject()
                        .orElseGet(() -> {
                            try {
                                return deserializer.deserializeUnsafe();
                            } catch (EventDataObjectDeserializationException e) {
                                throw new RuntimeException(e);
                            }
                        });

                if (intent != null) {
                    System.out.println("✅ Payment succeeded for Intent ID: {}"+ intent.getId());
                    paymentService.updatePaymentStatus(intent.getId(), PaymentStatus.SUCCESS.name(), null);
                } else {
                    System.out.println("❌ Failed to deserialize PaymentIntent for event: {}"+ event.getId());
                }
            }
            case "payment_intent.payment_failed" -> {
                var deserializer = event.getDataObjectDeserializer();

                PaymentIntent intent = (PaymentIntent) deserializer.getObject()
                        .orElseGet(() -> {
                            try {
                                return deserializer.deserializeUnsafe();
                            } catch (EventDataObjectDeserializationException e) {
                                throw new RuntimeException(e);
                            }
                        });

                if (intent != null) {
                    String reason = intent.getLastPaymentError() != null
                            ? intent.getLastPaymentError().getMessage()
                            : "Payment failed";
                    System.out.println("❌ Payment failed for ID: {}. Reason: {}"+ intent.getId()+" "+ reason);
                    paymentService.updatePaymentStatus(intent.getId(), "FAILED", reason);
                }
            }
           // default -> log.info("Unhandled event type: {}", event.getType());
            default -> System.out.println("Unhandled event type: {}"+ event.getType());
        }

        return ResponseEntity.ok("OK");
    }
}