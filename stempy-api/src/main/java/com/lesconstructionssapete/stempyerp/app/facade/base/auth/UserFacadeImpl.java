package com.lesconstructionssapete.stempyerp.app.facade.base.auth;

import java.util.List;

import com.lesconstructionssapete.stempyerp.core.config.db.TransactionManager;
import com.lesconstructionssapete.stempyerp.core.config.db.TransactionPropagation;
import com.lesconstructionssapete.stempyerp.core.repository.base.auth.UserRepository;
import com.lesconstructionssapete.stempyerp.domain.base.auth.User;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;

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
