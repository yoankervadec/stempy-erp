package com.lesconstructionssapete.stempyerp.domain.repository.auth;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;

public interface UserCredentialRepository {

  List<UserCredential> fetch(Connection connection, DomainQuery query);

}
