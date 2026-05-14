package com.stripe.payment.controller;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/stripe")
public class StripeWebhookController {

    @Value("${stripe.webhook.secret:test}")
    private String endpointSecret;

    @PostMapping("/webhook")
    public String handleStripeEvent(@RequestBody String payload,
                                    @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            log.error("⚠️ Webhook signature verification failed: {}", e.getMessage());
            return "Invalid signature";
        }

        // Handle event type
        switch (event.getType()) {
            case "payment_intent.succeeded" -> {
                log.info("✅ Payment succeeded: {}", event.getDataObjectDeserializer().getRawJson());
                // TODO: save order/payment info to database
            }
            case "payment_intent.payment_failed" -> {
                log.warn("❌ Payment failed: {}", event.getDataObjectDeserializer().getRawJson());
                // TODO: handle failed payment
            }
            default -> log.info("Unhandled event type: {}", event.getType());
        }

        return "OK";
    }
}