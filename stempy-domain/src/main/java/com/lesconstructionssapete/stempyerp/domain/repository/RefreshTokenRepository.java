package com.lesconstructionssapete.stempyerp.domain.repository;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;

public interface RefreshTokenRepository {

  long insert(Connection connection, AuthToken token);

  List<AuthToken> fetch(Connection connection, DomainQuery query);
}
