package com.lesconstructionssapete.stempyerp.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.User;
import com.lesconstructionssapete.stempyerp.domain.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;

public interface UserRepository {

  List<User> fetch(Connection connection, DomainQuery query) throws SQLException;

  List<UserCredential> fetchCredentials(Connection connection, DomainQuery query) throws SQLException;

}
