package com.lesconstructionssapete.stempyerp.service.user;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.user.User;

public interface UserService {

  List<User> fetch(Connection connection, DomainQuery query);
}
