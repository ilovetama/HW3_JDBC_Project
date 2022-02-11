package org.epam.training.databaseQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.epam.training.model.User;
import org.epam.training.service.ConnectionToDB;

public class UserService {

  private static Connection connection = null;
  private static Statement statement = null;
  private static PreparedStatement preparedStatement = null;

  public void addUserToDataBase(User user) throws SQLException {
    try {
      connection = ConnectionToDB.getConnection();
      preparedStatement = connection.prepareStatement(
          "INSERT INTO USERS (userId, name, address) VALUES ( ?, ?, ?)");
      preparedStatement.setInt(1, user.getUserId());
      preparedStatement.setString(2, user.getName());
      preparedStatement.setString(3, user.getAddress());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      ConnectionToDB.closeConnection(connection, preparedStatement);
    }
  }

  public ArrayList<String> checkUserId() throws SQLException {
    ArrayList<String> userIdList = new ArrayList<>();
    try {
      connection = ConnectionToDB.getConnection();
      statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT userId FROM USERS");
      ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
      int columnCount = resultSetMetaData.getColumnCount();
      userIdList = new ArrayList<>(columnCount);
      while (resultSet.next()) {
        int i = 1;
        while (i <= columnCount) {
          userIdList.add(resultSet.getString(i++));
        }
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      ConnectionToDB.closeConnection(connection, statement);
    }
    return (userIdList);
  }
}