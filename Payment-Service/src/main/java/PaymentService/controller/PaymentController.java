package PaymentService.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import PaymentService.entity.Payment;
import PaymentService.entity.PaymentRequest;
import PaymentService.entity.PaymentResponse;
import PaymentService.service.PaymentService;
import PaymentService.service.StripeService;





@RestController
@RequestMapping("/payment")
public class PaymentController {
  @Autowired
  private PaymentService service;


	  private final StripeService stripeService;

	    @Value("${stripe.publishable.key}")
	    private String publishableKey;

	    public PaymentController(StripeService stripeService) {
	        this.stripeService = stripeService;
	    }

	    @GetMapping("/config")
	    public ResponseEntity<String> getStripePublishableKey() {
	        return ResponseEntity.ok(publishableKey);
	    }

	    @PostMapping("/doPayment")
	    public ResponseEntity<PaymentResponse> createPaymentIntent(@RequestBody PaymentRequest request) throws Exception {
	        try {
	            System.out.println("inside createpayment--->" + request.getCurrency() + " --- " + request.getAmount());
	            
	            if (request.getAmount() == null) {
	                throw new Exception("Amount is required");
	            }

	            PaymentIntent paymentIntent = stripeService.createPaymentIntent(request.getAmount(), request.getCurrency());
	            PaymentResponse response = new PaymentResponse();
	            
	            System.out.println("inside createpayment22 --->" + paymentIntent.getClientSecret());
	            response.setClientSecret(paymentIntent.getClientSecret());
	            System.out.println("inside createpayment22 --->" + paymentIntent.getClientSecret());
	            response.setStatus(paymentIntent.getStatus());
	            response.setMessage("Payment Intent created successfully.");
	            return ResponseEntity.ok(response);
	        } catch (StripeException e) {
	            System.err.println("Stripe Exception: " + e.getMessage());
	            PaymentResponse response = new PaymentResponse();
	            response.setMessage("Error creating Payment Intent: " + e.getMessage());
	            response.setStatus("failed");
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        }
	    }

	    // This endpoint could be used to confirm a PaymentIntent if not handled entirely client-side
	    // or to retrieve its status after a client-side confirmation.
	    @PostMapping("/confirm-payment")
	    public ResponseEntity<PaymentResponse> confirmPayment(@RequestBody PaymentRequest request) {
	        try {
	            PaymentIntent paymentIntent = stripeService.confirmPaymentIntent(request.getPaymentMethodId()); // paymentMethodId here refers to paymentIntentId
	            PaymentResponse response = new PaymentResponse();
	            response.setClientSecret(paymentIntent.getClientSecret());
	            response.setStatus(paymentIntent.getStatus());
	            if (paymentIntent.getStatus().equals("succeeded")) {
	                response.setMessage("Payment succeeded!");
	            } else if (paymentIntent.getStatus().equals("requires_action")) {
	                response.setMessage("Payment requires additional action (e.g., 3D Secure).");
	            } else {
	                response.setMessage("Payment status: " + paymentIntent.getStatus());
	            }
	            return ResponseEntity.ok(response);
	        } catch (StripeException e) {
	            System.err.println("Stripe Exception: " + e.getMessage());
	            PaymentResponse response = new PaymentResponse();
	            response.setMessage("Error confirming Payment Intent: " + e.getMessage());
	            response.setStatus("failed");
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        }
	    
  }

  @GetMapping("/{orderId}")
  public Payment findPaymentHistoryByOrderId(int orderId) {
    return service.findPaymentHistoryByOrderId(orderId);
  }

}
