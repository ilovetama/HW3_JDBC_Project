package org.epam.training.databaseQueries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
}
