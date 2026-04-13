package com.lesconstructionssapete.stempyerp.infrastructure.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;

import com.lesconstructionssapete.stempyerp.port.persistence.SQLConnectionProvider;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.github.cdimascio.dotenv.Dotenv;

public class HikariConnectionProvider implements SQLConnectionProvider {

  private final HikariDataSource dataSource;

  public HikariConnectionProvider() {
    Dotenv dotenv = Dotenv.load();

    HikariConfig config = new HikariConfig();

    config.setPoolName("StempyERPConnectionPool");

    config.setJdbcUrl(dotenv.get("DB_URL"));
    config.setUsername(dotenv.get("DB_USER"));
    config.setPassword(dotenv.get("DB_PASSWORD"));

    String poolSize = dotenv.get("DB_MAX_POOL_SIZE", "6");
    config.setMaximumPoolSize(Integer.parseInt(poolSize));
    config.setMinimumIdle(2);
    config.setConnectionTimeout(Duration.ofSeconds(30).toMillis());
    config.setIdleTimeout(Duration.ofMinutes(10).toMillis());
    config.setMaxLifetime(Duration.ofMinutes(30).toMillis());

    config.setDriverClassName("com.mysql.cj.jdbc.Driver");

    // MySQL specific optimizations
    config.addDataSourceProperty("cachePrepStmts", "true"); // enable prepared statement caching
    config.addDataSourceProperty("prepStmtCacheSize", "100"); // number of prepared statements to cache
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048"); // character limit for cached SQL

    this.dataSource = new HikariDataSource(config);

  }

  @Override
  public Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

  @Override
  public void close() {
    if (dataSource != null) {
      dataSource.close();
    }
  }
}
