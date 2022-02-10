package org.epam.training.databaseQueries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import org.epam.training.model.User;
import org.epam.training.service.Constant;

public class UserService {

  public void addUserToDataBase(User user) {
    try {
      Class.forName(Constant.JDBC_DRIVER);
      Connection connection = DriverManager.getConnection(Constant.DATABASE_URL);
      try {
        PreparedStatement statement = connection.prepareStatement
            ("INSERT INTO USERS (userId, name, address) VALUES ( ?, ?, ?)");
        statement.setInt(1, user.getUserId());
        statement.setString(2, user.getName());
        statement.setString(3, user.getAddress());
        statement.executeUpdate();
        statement.close();
      } finally {
        connection.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public ArrayList<String> checkUserId() {
    ArrayList<String> userIdList = new ArrayList<>();
    try {
      Class.forName(Constant.JDBC_DRIVER);
      Connection connection = DriverManager.getConnection(Constant.DATABASE_URL);
      try {
        Statement statement = connection.createStatement();
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
        resultSet.close();
        statement.close();
      } finally {
        connection.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return (userIdList);
  }

  public User getUserInfo(User user) {
    try {
      Class.forName(Constant.JDBC_DRIVER);
      Connection connection = DriverManager.getConnection(Constant.DATABASE_URL);
      try {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
            "SELECT * FROM USERS WHERE userId = " + "'" + user.getUserId() + "'");
        while (resultSet.next()) {
              user.setName(resultSet.getString("name"));
              user.setAddress(resultSet.getString("address"));
        }
        resultSet.close();
        statement.close();
      } finally {
        connection.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return user;
  }

}
