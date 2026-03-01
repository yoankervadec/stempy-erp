package com.lesconstructionssapete.stempyerp.facade.base.auth;

import java.util.List;

import com.lesconstructionssapete.stempyerp.config.db.TransactionManager;
import com.lesconstructionssapete.stempyerp.config.db.TransactionPropagation;
import com.lesconstructionssapete.stempyerp.domain.base.auth.User;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.repository.UserRepository;

public class UserFacadeImpl implements UserFacade {

  private final UserRepository userRepository;

  public UserFacadeImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> fetch(DomainQuery query) {

    return TransactionManager.execute(
        TransactionPropagation.REQUIRED,
        con -> {
          return userRepository.fetch(con, query);
        });
  }

}
