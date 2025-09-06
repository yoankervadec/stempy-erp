package com.lesconstructions.sapete.stempyerp.core.repository.base.user;

import java.sql.Connection;
import java.sql.SQLException;

import com.lesconstructions.sapete.stempyerp.core.domain.base.user.User;

public interface UserRepository {

  User validateCredentials(Connection connection, String usernameLong, String password) throws SQLException;
}
