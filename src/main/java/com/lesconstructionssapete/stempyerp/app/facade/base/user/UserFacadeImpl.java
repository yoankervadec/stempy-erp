package com.lesconstructionssapete.stempyerp.app.facade.base.user;

import java.sql.Connection;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.core.config.db.ConnectionPool;
import com.lesconstructionssapete.stempyerp.core.domain.base.user.User;
import com.lesconstructionssapete.stempyerp.core.domain.base.user.UserCredential;
import com.lesconstructionssapete.stempyerp.core.repository.base.user.UserRepository;

public class UserFacadeImpl implements UserFacade {

  private final UserRepository userRepository;

  public UserFacadeImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User validateCredentials(UserCredential userCredential) {

    Connection con = null;

    try {
      con = ConnectionPool.getConnection();
      con.setAutoCommit(false);

      User user = userRepository.validateCredentials(con, userCredential);

      con.commit();
      return user;
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
  public User findByUserNo(String userNo) {
    Connection con = null;

    try {
      con = ConnectionPool.getConnection();
      con.setAutoCommit(false);

      User user = userRepository.findByUserNo(con, userNo);

      con.commit();
      return user;
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
