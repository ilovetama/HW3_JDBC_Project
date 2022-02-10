package org.epam.training.model;

public class Account {

  private Double balance;
  private String currency;

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  private int userId;

  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  @Override
  public String toString() {
    return "Account{" +
        "balance=" + balance +
        ", currency='" + currency + '\'' +
        '}';
  }
}
