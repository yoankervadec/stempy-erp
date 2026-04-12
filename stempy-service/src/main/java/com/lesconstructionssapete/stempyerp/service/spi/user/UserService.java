package com.lesconstructionssapete.stempyerp.service.spi.user;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.user.User;

public interface UserService {

  List<User> fetch(Connection connection, DomainQuery query);
}
