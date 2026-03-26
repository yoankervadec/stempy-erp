package com.lesconstructionssapete.stempyerp.repository;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;

public interface RefreshTokenRepository {

  long insert(Connection connection, AuthToken token);

  List<AuthToken> fetch(Connection connection, DomainQuery query);
}
