package com.stripe.payment.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PaymentRequest {
        @NotNull(message = "Amount is required")
        @Min(value = 50, message = "Amount must be at least 50 cents ($0.50)")
        private Long amount; // Amount in the smallest currency unit (e.g., cents for USD/EUR, paise for INR)

        @NotBlank(message = "Currency is required")
        private String currency; // e.g., "usd", "inr", "eur"

        // Optional fields if you want to attach metadata or customer details
        private String description;
        private String receiptEmail;

        // Default Constructor (Required for Jackson deserialization)
        public PaymentRequest() {
        }

        public PaymentRequest(Long amount, String currency) {
            this.amount = amount;
            this.currency = currency;
        }

        // Getters and Setters
        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getReceiptEmail() {
            return receiptEmail;
        }

        public void setReceiptEmail(String receiptEmail) {
            this.receiptEmail = receiptEmail;
        }
}