package PaymentService.entity;

public class PaymentResponse {
    private String clientSecret;
    private String message;
    private String status; // e.g., "requires_action", "succeeded", "failed"
	public PaymentResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PaymentResponse(String clientSecret, String message, String status) {
		super();
		this.clientSecret = clientSecret;
		this.message = message;
		this.status = status;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public String getMessage() {
		return message;
	}
	public String getStatus() {
		return status;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
    
}