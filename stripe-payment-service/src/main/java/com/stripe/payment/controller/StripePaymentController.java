package com.stripe.payment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.payment.entity.StripePaymentEntity;
import com.stripe.payment.repository.StripePaymentRepository;
import com.stripe.payment.service.StripePaymentService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/api/payments")
public class StripePaymentController {

    @Value("${stripe.public.key}")
    private String stripePublicKey;
    
    @Autowired
    StripePaymentService paymentService;
    
    @Autowired
    private StripePaymentRepository paymentRepository;

    @GetMapping("/payment")
    public String paymentPage() {
        return "payment"; // Spring automatically resolves payment.html from templates/
    }

    @PostMapping("/create-payment-intent")
    @ResponseBody
    public Map<String, Object> createPaymentIntent(@RequestBody Map<String, Object> data) throws StripeException {
        Long amount = ((Number) data.get("amount")).longValue(); // in cents

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("currency", "usd");
        params.put("automatic_payment_methods", Map.of("enabled", true));

        PaymentIntent intent = PaymentIntent.create(params);

        paymentService.savePayment(intent.getId(), data );
        
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("clientSecret", intent.getClientSecret());
        responseData.put("publicKey", stripePublicKey);

        return responseData;
    }
    
 // ✅ Fetch all payments
    @GetMapping
    public ResponseEntity<List<StripePaymentEntity>> getAllPayments() {
        List<StripePaymentEntity> payments = paymentRepository.findAll();
        log.info("Fetched {} payments", payments.size());
        return ResponseEntity.ok(payments);
    }

    // ✅ Fetch a single payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<StripePaymentEntity> getPaymentById(@PathVariable Long id) {
        return paymentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Fetch payments by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<StripePaymentEntity>> getPaymentsByStatus(@PathVariable String status) {
        List<StripePaymentEntity> payments = paymentRepository.findAll()
                .stream()
                .filter(p -> p.getStatus() != null && p.getStatus().equalsIgnoreCase(status))
                .toList();
        return ResponseEntity.ok(payments);
    }
}