package PaymentService.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.secret.key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public PaymentIntent createPaymentIntent(Long amount, String currency) throws StripeException {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(amount) // amount in cents
                        .setCurrency(currency)
                        .addPaymentMethodType("card")
                        .setDescription("Example charge for an order")
                                        .build();
                                        
                    

        return PaymentIntent.create(params);
    }

    public PaymentIntent confirmPaymentIntent(String paymentIntentId) throws StripeException {
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        // PaymentIntent can be confirmed on the server side if needed, but often
        // confirmed on the client-side for 3D Secure flows.
        // For simplicity, we'll assume it's already confirmed or confirm here.
        if (!paymentIntent.getStatus().equals("succeeded")) {
            return paymentIntent.confirm();
        }
        return paymentIntent;
    }
}