package com.lesconstructions.sapete.stempyerp.core.config.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.github.cdimascio.dotenv.Dotenv;

public class ConnectionPool {

  private static HikariDataSource dataSource;

  static {
    try {
      Dotenv dotenv = Dotenv.load();

      // HikariConfig config = new HikariConfig("/db.properties");
      HikariConfig config = new HikariConfig();

      config.setJdbcUrl(dotenv.get("DB_URL"));
      config.setUsername(dotenv.get("DB_USER"));
      config.setPassword(dotenv.get("DB_PASSWORD"));
      String poolSize = dotenv.get("DB_MAX_POOL_SIZE", "10");
      config.setMaximumPoolSize(Integer.parseInt(poolSize));
      config.setDriverClassName("com.mysql.cj.jdbc.Driver");

      dataSource = new HikariDataSource(config);

    } catch (NumberFormatException e) {
      throw new RuntimeException("Failed to initialize connection pool", e);
    }
  }

  public static Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

  public static void close() {
    if (dataSource != null) {
      dataSource.close();
    }
  }

}
