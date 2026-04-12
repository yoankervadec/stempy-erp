package com.lesconstructionssapete.stempyerp.repository;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.query.DomainQuery;

public interface RefreshTokenRepository {

  long insert(Connection connection, AuthToken token);

  List<AuthToken> fetch(Connection connection, DomainQuery query);
}
