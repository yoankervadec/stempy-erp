package com.lesconstructionssapete.stempyerp.app.facade.base.auth;

import java.sql.Connection;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.core.config.db.ConnectionPool;
import com.lesconstructionssapete.stempyerp.core.domain.base.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.core.repository.base.auth.RefreshTokenRepository;

public class AuthFacadeImpl implements AuthFacade {

  private final RefreshTokenRepository refreshTokenRepository;

  public AuthFacadeImpl(RefreshTokenRepository refreshTokenRepository) {
    this.refreshTokenRepository = refreshTokenRepository;
  }

  @Override
  public boolean exists(String userNo, String refreshToken) {

    Connection con = null;

    try {
      con = ConnectionPool.getConnection();
      con.setAutoCommit(false);

      boolean exists = refreshTokenRepository.exists(con, userNo, refreshToken);

      con.commit();
      return exists;
    } catch (SQLException e) {
      if (con != null)
        try {
          con.rollback();
        } catch (SQLException e1) {
        }
      throw new RuntimeException("Failed...", e);

    } finally {
      if (con != null)
        try {
          con.close();
        } catch (SQLException e) {
        }
    }

  }

  @Override
  public AuthToken save(AuthToken refreshToken) {

    Connection con = null;

    try {
      con = ConnectionPool.getConnection();
      con.setAutoCommit(false);

      refreshTokenRepository.save(con, refreshToken);

      con.commit();
      return refreshToken;
    } catch (SQLException e) {
      if (con != null)
        try {
          con.rollback();
        } catch (SQLException e1) {
        }
      throw new RuntimeException("Failed...", e);

    } finally {
      if (con != null)
        try {
          con.close();
        } catch (SQLException e) {
        }
    }
  }

}
