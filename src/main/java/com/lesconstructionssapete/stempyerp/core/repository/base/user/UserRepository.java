package com.lesconstructionssapete.stempyerp.core.repository.base.user;

import java.sql.Connection;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.core.domain.base.user.User;
import com.lesconstructionssapete.stempyerp.core.domain.base.user.UserCredential;

public interface UserRepository {

  User validateCredentials(Connection connection, UserCredential userCredential) throws SQLException;

  User findByUserNo(Connection connection, String userNo) throws SQLException;
}
