package PaymentService.entity;

public class PaymentRequest {
    private Long amount; // in cents
    private String currency;
    private String paymentMethodId; // Token or PaymentMethod ID from frontend
	public PaymentRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PaymentRequest(Long amount, String currency, String paymentMethodId) {
		super();
		this.amount = amount;
		this.currency = currency;
		this.paymentMethodId = paymentMethodId;
	}
	public Long getAmount() {
		return amount;
	}
	public String getCurrency() {
		return currency;
	}
	public String getPaymentMethodId() {
		return paymentMethodId;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public void setPaymentMethodId(String paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}
    
    
    
}

