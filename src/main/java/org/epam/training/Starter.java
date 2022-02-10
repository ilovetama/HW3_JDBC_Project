package org.epam.training;

import org.epam.training.databaseQueries.AccountService;
import org.epam.training.databaseQueries.TransactionService;
import org.epam.training.databaseQueries.UserService;
import org.epam.training.model.Account;
import org.epam.training.service.InputData;

public class Starter {

  public static void main(String[] args) {

    InputData inputData = new InputData();
    UserService userService = new UserService();
    AccountService accountService = new AccountService();
    TransactionService transactionService = new TransactionService();
    Account account;
    int menuOption = inputData.inputMenuOptions();
    switch (menuOption) {
      case 1 -> userService.addUserToDataBase(inputData.createNewUser());
      case 2 -> {
        account = inputData.createNewAccount();
        accountService.createNewAccount(account);
        accountService.showCurrentAccount(account);
        transactionService.createPopUpTransaction(account);
      }
      case 3 -> {
        account = inputData.popUpBalance();
        accountService.popUpBalance(account);
        accountService.showCurrentAccount(account);
        transactionService.createPopUpTransaction(account);
      }
      case 4 -> {
        account = inputData.withdrawalCash();
        accountService.withdrawalCash(account);
        accountService.showCurrentAccount(account);
        transactionService.createWithdrawalTransaction(account);
      }
      case 5 -> System.exit(0);
    }
  }
}
