package com.lesconstructionssapete.stempyerp.repository.auth;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.query.DomainQuery;

public interface UserCredentialRepository {

  List<UserCredential> fetch(Connection connection, DomainQuery query);

}
