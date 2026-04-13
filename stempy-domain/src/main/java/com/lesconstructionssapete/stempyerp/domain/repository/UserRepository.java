package com.lesconstructionssapete.stempyerp.domain.repository;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.user.User;

public interface UserRepository {

  List<User> fetch(Connection connection, DomainQuery query);

}
