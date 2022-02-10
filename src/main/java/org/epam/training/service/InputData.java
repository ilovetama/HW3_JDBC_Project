package org.epam.training.service;

import java.util.Scanner;
import org.epam.training.databaseQueries.AccountService;
import org.epam.training.databaseQueries.UserService;
import org.epam.training.model.Account;
import org.epam.training.model.User;

public class InputData {

  UserService userService = new UserService();
  AccountService accountService = new AccountService();

  public int inputMenuOptions() {

    System.out.println("""
        Please choose operation:
        1 - Create new user
        2 - Create new account
        3 - Pop up balance
        4 - Withdrawal cash
        5 - Show my accounts
        6 - Exit""");
    Scanner scanner = new Scanner(System.in);
    return scanner.nextInt();
  }

  public User showMyAccount() {
    User user = new User();
    System.out.println("Enter your phone number:");
    Scanner scanner = new Scanner(System.in);
    user.setUserId(scanner.nextInt());
    return user;
  }

  public User createNewUser() {
    User user = new User();
    System.out.println("Enter your phone number:");
    Scanner scanner = new Scanner(System.in);
    user.setUserId(scanner.nextInt());
    isUserExist(user);
    System.out.println("Enter user name:");
    scanner = new Scanner(System.in);
    user.setName(scanner.next());
    System.out.println("Enter address:");
    scanner = new Scanner(System.in);
    user.setAddress(scanner.next());
    return user;
  }

  public Account createNewAccount() {
    Account account = new Account();
    System.out.println("Enter your phone number:");
    Scanner scanner = new Scanner(System.in);
    account.setUserId(scanner.nextInt());
    System.out.println("Enter currency of your deposit: EUR, USD, BYN");
    scanner = new Scanner(System.in);
    account.setCurrency(scanner.next());
    isAccountExist(account);
    System.out.println("Enter the amount you want to deposit:");
    scanner = new Scanner(System.in);
    account.setBalance(
        Math.round((scanner.nextDouble()) * (int) Math.pow(10.0, 3)) / Math.pow(10.0, 3));
    return account;
  }

  public Account popUpBalance() {
    Account account = new Account();
    System.out.println("Enter your phone number:");
    Scanner scanner = new Scanner(System.in);
    account.setUserId(scanner.nextInt());
    System.out.println("Enter currency of your account: EUR, USD, BYN");
    scanner = new Scanner(System.in);
    account.setCurrency(scanner.next());
    System.out.println("Enter the amount:");
    scanner = new Scanner(System.in);
    account.setBalance(
        Math.round((scanner.nextDouble()) * (int) Math.pow(10.0, 3)) / Math.pow(10.0, 3));
    isAmountCorrect(account);
    return account;
  }

  public Account withdrawalCash() {
    Account account = new Account();
    System.out.println("Enter your phone number:");
    Scanner scanner = new Scanner(System.in);
    account.setUserId(scanner.nextInt());
    System.out.println("Enter currency of your account: EUR, USD, BYN");
    scanner = new Scanner(System.in);
    account.setCurrency(scanner.next());
    System.out.println("Enter the amount:");
    scanner = new Scanner(System.in);
    account.setBalance(
        Math.round((scanner.nextDouble()) * (int) Math.pow(10.0, 3)) / Math.pow(10.0, 3));
    isBalanceBelowZero(account);
    return account;
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

  private void isAccountExis(Account account) {
    if (accountService.checkCurrency(account.getUserId())
        .contains(account.getCurrency())) {
      System.out.println("You already have account in " + account.getCurrency());
      System.exit(0);
    }
  }

}
