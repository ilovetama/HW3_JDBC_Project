package org.epam.training.databaseQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.epam.training.model.Account;
import org.epam.training.service.ConnectionToDB;

public class TransactionService {

  private static Connection connection = null;
  private static PreparedStatement preparedStatement = null;

  private void setPreparedStatement(Account account) throws SQLException {
    preparedStatement.setString(1, account.getCurrency());
    preparedStatement.setInt(2, account.getUserId());
    preparedStatement.setDouble(3, account.getBalance());
    preparedStatement.executeUpdate();
  }

  public void createPopUpTransaction(Account account) throws SQLException {
      try {
        connection = ConnectionToDB.getConnection();
        preparedStatement = connection.prepareStatement(
            "INSERT INTO TRANSACTIONS (accountId,amount) VALUES"
                + " ((SELECT accountId FROM ACCOUNTS WHERE currency = ? AND userId = ?),?)");
        setPreparedStatement(account);
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      } finally {
        ConnectionToDB.closeConnection(connection, preparedStatement);
      }
  }

  public void createWithdrawalTransaction(Account account) throws SQLException {
      try {
        connection = ConnectionToDB.getConnection();
        preparedStatement = connection.prepareStatement(
            "INSERT INTO TRANSACTIONS (accountId,amount) VALUES"
                + " ((SELECT accountId FROM ACCOUNTS WHERE currency = ? AND userId = ?),-?)");
        setPreparedStatement(account);
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      } finally {
        ConnectionToDB.closeConnection(connection, preparedStatement);
      }
  }
}
