package org.epam.training.databaseQueries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.epam.training.model.Account;
import org.epam.training.service.Constant;

public class AccountService {

  public void createNewAccount(Account account) {
    try {
      Class.forName(Constant.JDBC_DRIVER);
      Connection connection = DriverManager.getConnection(Constant.DATABASE_URL);
      try {
        PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO ACCOUNTS (userId,balance,currency) VALUES (?,?,?)");
        statement.setInt(1, account.getUserId());
        statement.setDouble(2, account.getBalance());
        statement.setString(3, account.getCurrency());
        statement.executeUpdate();
        statement.close();
      } finally {
        connection.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void popUpBalance(Account account) {
    try {
      Class.forName(Constant.JDBC_DRIVER);
      Connection connection = DriverManager.getConnection(Constant.DATABASE_URL);
      try {
        PreparedStatement statement = connection.prepareStatement(
            "UPDATE ACCOUNTS SET balance = balance + ? WHERE userId = ?"
                + " AND currency = ?");
        statement.setDouble(1, account.getBalance());
        statement.setInt(2, account.getUserId());
        statement.setString(3, account.getCurrency());
        statement.executeUpdate();
        statement.close();
      } finally {
        connection.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void withdrawalCash(Account account) {
    try {
      Class.forName(Constant.JDBC_DRIVER);
      Connection connection = DriverManager.getConnection(Constant.DATABASE_URL);
      try {
        PreparedStatement statement = connection.prepareStatement(
            "UPDATE ACCOUNTS SET balance = balance - ? WHERE userId = ?"
                + " AND currency = ?");
        statement.setDouble(1, account.getBalance());
        statement.setInt(2, account.getUserId());
        statement.setString(3, account.getCurrency());
        statement.executeUpdate();
        statement.close();
      } finally {
        connection.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void showCurrentAccount(Account account) {
    try {
      Class.forName(Constant.JDBC_DRIVER);
      Connection connection = DriverManager.getConnection(Constant.DATABASE_URL);
      try {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
            "SELECT * FROM ACCOUNTS WHERE currency = " + "'" + account.getCurrency()
                + "'" + "AND userId = " + "'" + account.getUserId() + "'");
        System.out.println("Your account:");
        while (resultSet.next()) {
          String str = "UserID: " + resultSet.getString("userId")
              + "\nBalance: " + resultSet.getDouble("balance")
              + "\nCurrency: " + resultSet.getString("currency");
          System.out.println(str);
          System.out.println("----------------");
        }
        resultSet.close();
        statement.close();
      } finally {
        connection.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
