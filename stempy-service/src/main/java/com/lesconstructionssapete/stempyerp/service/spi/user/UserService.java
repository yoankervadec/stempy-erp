package com.lesconstructionssapete.stempyerp.service.spi.user;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.user.User;

public interface UserService {

  List<User> fetch(Connection connection, DomainQuery query);
}
