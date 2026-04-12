package com.lesconstructionssapete.stempyerp.repository;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.user.User;

public interface UserRepository {

  List<User> fetch(Connection connection, DomainQuery query);

}
