package org.epam.training.model;

public class Transaction {

  private Double amount;

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  @Override
  public String toString() {
    return "Transaction{" +
        "amount=" + amount +
        '}';
  }
}
