package org.epam.training.databaseQueries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.epam.training.model.Account;
import org.epam.training.model.User;
import org.epam.training.service.Constant;

public class AccountService {

  private static Connection connection = null;
  private static PreparedStatement statement = null;
  private double balance;

  public void createNewAccount(Account account) throws SQLException {
    try {
      connection = Constant.getConnection();
      statement = connection.prepareStatement
          ("INSERT INTO ACCOUNTS (userId,balance,currency) VALUES (?,?,?)");
      statement.setInt(1, account.getUserId());
      statement.setDouble(2, account.getBalance());
      statement.setString(3, account.getCurrency());
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      Constant.closeConnection(connection, statement);
    }
  }

  public void popUpBalance(Account account) throws SQLException {
    try {
      connection = Constant.getConnection();
      statement = connection.prepareStatement("UPDATE ACCOUNTS SET"
          + " balance = balance + ? WHERE userId = ? AND currency = ?");
      statement.setDouble(1, account.getBalance());
      statement.setInt(2, account.getUserId());
      statement.setString(3, account.getCurrency());
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      Constant.closeConnection(connection, statement);
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

  public void showMyAccount(User user) {
    try {
      Class.forName(Constant.JDBC_DRIVER);
      Connection connection = DriverManager.getConnection(Constant.DATABASE_URL);
      try {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
            "SELECT * FROM ACCOUNTS WHERE userId = " + "'" + user.getUserId() + "'");
        System.out.println("Your accounts:");
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

  public double checkBalance(Account account) {
    try {
      Class.forName(Constant.JDBC_DRIVER);
      Connection connection = DriverManager.getConnection(Constant.DATABASE_URL);
      try {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT balance"
            + " FROM ACCOUNTS WHERE userId =" + "'" + account.getUserId() + "'" + " AND"
            + " currency = " + "'" + account.getCurrency() + "'");
        while (resultSet.next()) {
          balance = resultSet.getDouble(1);
        }
        resultSet.close();
        statement.close();
      } finally {
        connection.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return (balance);
  }

  public ArrayList<String> checkCurrency(int userId) {
    ArrayList<String> userCurrencyList = new ArrayList<>();
    try {
      Class.forName(Constant.JDBC_DRIVER);
      Connection connection = DriverManager.getConnection(Constant.DATABASE_URL);
      try {
        PreparedStatement statement = connection.prepareStatement(
            "SELECT currency FROM ACCOUNTS WHERE userId = ?");
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        userCurrencyList = new ArrayList<>(columnCount);
        while (resultSet.next()) {
          int i = 1;
          while (i <= columnCount) {
            userCurrencyList.add(resultSet.getString(i++));
          }
        }
        resultSet.close();
        statement.close();
      } finally {
        connection.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return (userCurrencyList);
  }

}
