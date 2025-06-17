package shipmentservice.entity;

import lombok.Data;


@Data
public class Payment {
	
  private int paymentId;
  private String paymentStatus;
  private String transactionId;
  private double amount;
  private String fullAddress;

  


}