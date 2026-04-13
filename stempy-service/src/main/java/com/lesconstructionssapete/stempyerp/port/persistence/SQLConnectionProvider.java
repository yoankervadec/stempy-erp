package com.lesconstructionssapete.stempyerp.port.persistence;

import java.sql.Connection;
import java.sql.SQLException;

public interface SQLConnectionProvider {

  Connection getConnection() throws SQLException;

  void close();

}
