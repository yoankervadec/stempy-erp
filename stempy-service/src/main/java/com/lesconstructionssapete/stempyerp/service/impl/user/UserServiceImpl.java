package com.lesconstructionssapete.stempyerp.service.impl.user;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.repository.UserRepository;
import com.lesconstructionssapete.stempyerp.service.spi.user.UserService;
import com.lesconstructionssapete.stempyerp.user.User;

public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> fetch(Connection connection, DomainQuery query) {

    return userRepository.fetch(connection, query);
  }

}
