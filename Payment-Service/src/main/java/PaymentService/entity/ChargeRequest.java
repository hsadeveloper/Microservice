package PaymentService.entity;


public class ChargeRequest {

    
    private String description;
    private int amount;
    private String stripeEmail;
    private String stripeToken;
      
	public ChargeRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChargeRequest(String description, int amount, String stripeEmail, String stripeToken) {
		super();
		this.description = description;
		this.amount = amount;
		this.stripeEmail = stripeEmail;
		this.stripeToken = stripeToken;
	}

	public String getDescription() {
		return description;
	}

	public int getAmount() {
		return amount;
	}

	
	public String getStripeEmail() {
		return stripeEmail;
	}

	public String getStripeToken() {
		return stripeToken;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setStripeEmail(String stripeEmail) {
		this.stripeEmail = stripeEmail;
	}

	public void setStripeToken(String stripeToken) {
		this.stripeToken = stripeToken;
	}
	    
}