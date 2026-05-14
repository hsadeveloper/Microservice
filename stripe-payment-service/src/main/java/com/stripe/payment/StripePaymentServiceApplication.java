package com.stripe.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//@EnableWebSecurity
@SpringBootApplication(scanBasePackages = {"com.commonlib.security","com.stripe.payment"})
public class StripePaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StripePaymentServiceApplication.class, args);
	}

}
