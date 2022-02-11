package org.epam.training.service;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionToDB {

  public static final String JDBC_DRIVER = "org.sqlite.JDBC";
  public static final String DATABASE_URL = "jdbc:sqlite:testdb.db";

  public static java.sql.Connection getConnection() {
    java.sql.Connection connection = null;
    try {
      Class.forName(JDBC_DRIVER);
    } catch (ClassNotFoundException e) {
      System.out.println(e.getMessage());
    }
    try {
      connection = DriverManager.getConnection(DATABASE_URL);
      return connection;
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return connection;
  }

  public static void closeConnection(java.sql.Connection connection, Statement statement)
      throws SQLException {
    if (statement != null) {
      statement.close();
    }
    if (connection != null) {
      connection.close();
    }
  }
}

