package orderservice.common;

import orderservice.entity.Order;

public class TransactionResponse {
  private Order order;
  private String transactionId;
  private double amount;
  private String message;

  public TransactionResponse(Order order, double amount, String transactionId, String message) {
    super();
    this.order = order;
    this.transactionId = transactionId;
    this.amount = amount;
    this.message = message;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }



}
