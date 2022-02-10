package org.epam.training.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Constant {

  public static final String JDBC_DRIVER = "org.sqlite.JDBC";
  public static final String DATABASE_URL = "jdbc:sqlite:testdb.db";

  public static Connection getConnection() {
    Connection dbConnection = null;
    try {
      Class.forName(JDBC_DRIVER);
    } catch (ClassNotFoundException e) {
      System.out.println(e.getMessage());
    }
    try {
      dbConnection = DriverManager.getConnection(DATABASE_URL);
      return dbConnection;
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return dbConnection;
  }

  public static void closeConnection(Connection connection, Statement statement)
      throws SQLException {
    if (statement != null) {
      statement.close();
    }
    if (connection != null) {
      connection.close();
    }
  }
}

