package org.epam.training.databaseQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.epam.training.model.Account;
import org.epam.training.service.ConnectionToDB;

public class AccountService {

  private static Connection connection = null;
  private static PreparedStatement preparedStatement = null;
  private static Statement statement = null;
  private double balance;

  public void createNewAccount(Account account) throws SQLException {
    try {
      connection = ConnectionToDB.getConnection();
      preparedStatement = connection.prepareStatement
          ("INSERT INTO ACCOUNTS (userId,balance,currency) VALUES (?,?,?)");
      preparedStatement.setInt(1, account.getUserId());
      preparedStatement.setDouble(2, account.getBalance());
      preparedStatement.setString(3, account.getCurrency());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      ConnectionToDB.closeConnection(connection, preparedStatement);
    }
  }

  public void popUpBalance(Account account) throws SQLException {
    try {
      connection = ConnectionToDB.getConnection();
      preparedStatement = connection.prepareStatement("UPDATE ACCOUNTS SET"
          + " balance = balance + ? WHERE userId = ? AND currency = ?");
      preparedStatement.setDouble(1, account.getBalance());
      preparedStatement.setInt(2, account.getUserId());
      preparedStatement.setString(3, account.getCurrency());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      ConnectionToDB.closeConnection(connection, preparedStatement);
    }
  }

  public void withdrawalCash(Account account) throws SQLException {
    try {
      connection = ConnectionToDB.getConnection();
      preparedStatement = connection.prepareStatement("UPDATE ACCOUNTS SET"
          + " balance = balance - ? WHERE userId = ? AND currency = ?");
      preparedStatement.setDouble(1, account.getBalance());
      preparedStatement.setInt(2, account.getUserId());
      preparedStatement.setString(3, account.getCurrency());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      ConnectionToDB.closeConnection(connection, preparedStatement);
    }
  }

  public void showCurrentAccount(Account account) throws SQLException {
    try {
      connection = ConnectionToDB.getConnection();
      statement = connection.createStatement();
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
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      ConnectionToDB.closeConnection(connection, statement);
    }
  }

  public double checkBalance(Account account) throws SQLException {
    try {
      connection = ConnectionToDB.getConnection();
      statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT balance"
          + " FROM ACCOUNTS WHERE userId =" + "'" + account.getUserId() + "'" + " AND"
          + " currency = " + "'" + account.getCurrency() + "'");
      while (resultSet.next()) {
        balance = resultSet.getDouble(1);
      }
      resultSet.close();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      ConnectionToDB.closeConnection(connection, statement);
    }
    return (balance);
  }

  public ArrayList<String> checkCurrency(int userId) throws SQLException {
    ArrayList<String> userCurrencyList = new ArrayList<>();
    try {
      connection = ConnectionToDB.getConnection();
      preparedStatement = connection.prepareStatement(
          "SELECT currency FROM ACCOUNTS WHERE userId = ?");
      preparedStatement.setInt(1, userId);
      ResultSet resultSet = preparedStatement.executeQuery();
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
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      ConnectionToDB.closeConnection(connection, preparedStatement);
    }
    return (userCurrencyList);
  }
}
