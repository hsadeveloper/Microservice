package orderservice.common;

import lombok.Data;

@Data
public class Payment {
	
  private int paymentId;
  private String paymentStatus;
  private String transactionId;
  private int orderId;
  private double amount;
  private String fullAddress;
  private double tax;
  private double total;

  


}
