package org.epam.training.service;

import java.util.Scanner;
import org.epam.training.databaseQueries.AccountService;
import org.epam.training.databaseQueries.UserService;
import org.epam.training.model.Account;
import org.epam.training.model.User;

public class InputData {

  User user = new User();
  Account account = new Account();
  UserService userService = new UserService();
  AccountService accountService = new AccountService();

  public int inputMenuOptions() {

    System.out.println("""
        Please choose operation:
        1 - Create new user
        2 - Create new account
        3 - Pop up balance
        4 - Withdrawal cash
        5 - Exit""");
    Scanner scanner = new Scanner(System.in);
    return scanner.nextInt();
  }

  public User createNewUser() {
    user.setUserId(getNumber());
    isUserExist(user);
    user.setName(getName());
    user.setAddress(getAddress());
    return user;
  }

  public Account createNewAccount() {
    account.setUserId(getNumber());
    account.setCurrency(getCurrency());
    isAccountExist(account);
    account.setBalance(
        Math.round((getBalance()) * (int) Math.pow(10.0, 3)) / Math.pow(10.0, 3));
    return account;
  }

  public Account popUpBalance() {
    account.setUserId(getNumber());
    account.setCurrency(getCurrency());
    account.setBalance(
        Math.round((getBalance()) * (int) Math.pow(10.0, 3)) / Math.pow(10.0, 3));
    isAmountCorrect(account);
    isTransactionCorrect(account);
    return account;
  }

  public Account withdrawalCash() {
    account.setUserId(getNumber());
    account.setCurrency(getCurrency());
    account.setBalance(
        Math.round((getBalance()) * (int) Math.pow(10.0, 3)) / Math.pow(10.0, 3));
    isBalanceBelowZero(account);
    isTransactionCorrect(account);
    return account;
  }

  private int getNumber() {
    System.out.println("Enter your phone number:");
    Scanner scanner = new Scanner(System.in);
    return scanner.nextInt();
  }

  private String getName() {
    System.out.println("Enter your name:");
    Scanner scanner = new Scanner(System.in);
    return scanner.next();
  }

  private String getAddress() {
    System.out.println("Enter your address:");
    Scanner scanner = new Scanner(System.in);
    return scanner.next();
  }

  private int getBalance() {
    System.out.println("Enter amount:");
    Scanner scanner = new Scanner(System.in);
    return scanner.nextInt();
  }

  private String getCurrency() {
    System.out.println("Enter account currency: EUR, USD, BYN");
    Scanner scanner = new Scanner(System.in);
    return scanner.next();
  }

  private void isUserExist(User user) {
    if (userService.checkUserId().contains(String.valueOf(user.getUserId()))) {
      System.out.println("You already have account");
      System.exit(0);
    }
  }

  private void isAccountExist(Account account) {
    if (accountService.checkCurrency(account.getUserId())
        .contains(account.getCurrency())) {
      System.out.println("You already have account in " + account.getCurrency());
      System.exit(0);
    }
  }

  private void isAmountCorrect(Account account) {
    if ((account.getBalance() + accountService.checkBalance(account)
        > 2000000000)) {
      System.out.println("Sorry, account limit is exceeded");
      System.exit(0);
    }
  }

  private void isBalanceBelowZero(Account account) {
    if (account.getBalance() > accountService.checkBalance(account)) {
      System.out.println("Sorry, insufficient funds");
      System.exit(0);
    }
  }

  private void isTransactionCorrect(Account account) {
    if (account.getBalance() > 100000000) {
      System.out.println("Sorry, transaction size exceeded (The limit is 100`000`000)");
      System.exit(0);
    }
  }
}
