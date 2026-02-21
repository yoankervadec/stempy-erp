package com.lesconstructionssapete.stempyerp.core.config.db;

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

      HikariConfig config = new HikariConfig();

      config.setJdbcUrl(dotenv.get("DB_URL"));
      config.setUsername(dotenv.get("DB_USER"));
      config.setPassword(dotenv.get("DB_PASSWORD"));

      String poolSize = dotenv.get("DB_MAX_POOL_SIZE", "6");
      config.setMaximumPoolSize(Integer.parseInt(poolSize));
      config.setMinimumIdle(2);
      config.setConnectionTimeout(30000); // 30 seconds
      config.setIdleTimeout(600000); // 10 minutes
      config.setMaxLifetime(1800000); // 30 minutes

      config.setDriverClassName("com.mysql.cj.jdbc.Driver");

      config.addDataSourceProperty("cachePrepStmts", "true"); // enable prepared statement caching
      config.addDataSourceProperty("prepStmtCacheSize", "100"); // number of prepared statements to cache
      config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048"); // character limit for cached SQL

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
