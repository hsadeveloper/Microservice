package PaymentService.entity;

;

//@Entity

public class Payment {
//  @Id
//  @GeneratedValue
  private int paymentId;
  private String paymentStatus;
  private String transactionId;
  private int orderId;
  private double amount;
  private String fullAddress;
  private double tax;
  private double total;
  

  public String getPaymentStatus() {
    return paymentStatus;
  }

  public void setPaymentStatus(String paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }



}