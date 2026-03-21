package com.lesconstructionssapete.stempyerp.repository.auth;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;

public interface UserCredentialRepository {

  List<UserCredential> fetch(Connection connection, DomainQuery query);

}
