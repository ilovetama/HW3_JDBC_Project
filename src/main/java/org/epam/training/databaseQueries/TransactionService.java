package org.epam.training.databaseQueries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import org.epam.training.model.Account;
import org.epam.training.service.Constant;

public class TransactionService {

  public void createPopUpTransaction(Account account) {
    try {
      Class.forName(Constant.JDBC_DRIVER);
      Connection connection = DriverManager.getConnection(Constant.DATABASE_URL);
      try {
        PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO TRANSACTIONS (accountId,amount) VALUES"
                + " ((SELECT accountId FROM ACCOUNTS WHERE currency = ? AND userId = ?),?)");
        statement.setString(1, account.getCurrency());
        statement.setInt(2, account.getUserId());
        statement.setDouble(3, account.getBalance());
        statement.executeUpdate();
        statement.close();
      } finally {
        connection.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void createWithdrawalTransaction(Account account) {
    try {
      Class.forName(Constant.JDBC_DRIVER);
      Connection connection = DriverManager.getConnection(Constant.DATABASE_URL);
      try {
        PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO TRANSACTIONS (accountId,amount) VALUES"
                + " ((SELECT accountId FROM ACCOUNTS WHERE currency = ? AND userId = ?),-?)");
        statement.setString(1, account.getCurrency());
        statement.setInt(2, account.getUserId());
        statement.setDouble(3, account.getBalance());
        statement.executeUpdate();
        statement.close();
      } finally {
        connection.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
