package com.lesconstructionssapete.stempyerp.service.auth;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.domain.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;

public interface AuthService {

  boolean isValidRefreshToken(Connection connection, long userId, String refreshToken);

  long insert(Connection connection, AuthToken token);

  boolean isValidCredential(Connection connection, long userId, String password);

  List<UserCredential> fetchUserCredentials(Connection connection, DomainQuery query);
}
