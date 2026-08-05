package com.stripe.payment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.payment.exception.PaymentNotFoundException;
import com.stripe.payment.model.PaymentDetails;
import com.stripe.payment.model.PaymentRequest;
import com.stripe.payment.service.StripePaymentService;
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

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.payment.entity.StripePaymentEntity;
import com.stripe.payment.repository.StripePaymentRepository;
import com.stripe.payment.service.PaymentService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/api/payments")
public class StripePaymentController {

    @Value("${stripe.public.key}")
    private String stripePublicKey;
    
    @Autowired
    PaymentService paymentService;

    @Autowired
    private StripePaymentService stripePaymentService;

    @GetMapping("/payment")
    public String paymentPage() {
        return "checkout"; // Spring automatically resolves payment.html from templates/
    }

   /* @PostMapping("/create-payment-intent")
    @ResponseBody
    public Map<String, Object> createPaymentIntent(@RequestBody Map<String, Object> data) throws StripeException {
        long amount = ((Number) data.get("amount")).longValue(); // in cents
        PaymentIntent intent = stripePaymentService.makePayment(amount);
        paymentService.savePayment(intent, data );
        
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("clientSecret", intent.getClientSecret());
        responseData.put("publicKey", stripePublicKey);

        return responseData;
    }*/
    
 // ✅ Fetch all payments
    @GetMapping
    public ResponseEntity<List<PaymentDetails>> getAllPayments() {
        List<PaymentDetails> payments = paymentService.getAllPayments();
        //log.info("Fetched {} payments", payments.size());
        return ResponseEntity.ok(payments);
    }

    // ✅ Fetch a single payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDetails> getPaymentById(@PathVariable Long id) throws PaymentNotFoundException {
        PaymentDetails paymentDetails = paymentService.getPaymentDetails(id);
        return ResponseEntity.ok(paymentDetails);
    }

    // ✅ Fetch payments by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentDetails>> getPaymentsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(paymentService.getAllPaymentsByStatus(status));
    }

    // Spring Boot Controller Endpoint: /api/payments/create-intent
    @PostMapping("/create-payment-intent")
    public ResponseEntity<Map<String, String>> createPaymentIntent(@RequestBody PaymentRequest paymentRequest) throws StripeException {
        PaymentIntent paymentIntent = stripePaymentService.cretePaymentIntent(paymentRequest);
        paymentService.savePayment(paymentIntent,paymentRequest);
        // Return ONLY the client_secret to the UI
        return ResponseEntity.ok(Map.of("clientSecret", paymentIntent.getClientSecret()));
    }
}