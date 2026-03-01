package com.lesconstructionssapete.stempyerp.core.repository.base.auth;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.base.auth.User;
import com.lesconstructionssapete.stempyerp.domain.base.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;

public interface UserRepository {

  List<User> fetch(Connection connection, DomainQuery query) throws SQLException;

  List<UserCredential> fetchCredentials(Connection connection, DomainQuery query) throws SQLException;

}
