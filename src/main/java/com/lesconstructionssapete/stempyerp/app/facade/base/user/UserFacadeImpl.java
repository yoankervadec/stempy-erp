package com.lesconstructionssapete.stempyerp.app.facade.base.user;

import com.lesconstructionssapete.stempyerp.core.config.db.TransactionManager;
import com.lesconstructionssapete.stempyerp.core.config.db.TransactionPropagation;
import com.lesconstructionssapete.stempyerp.core.domain.base.user.User;
import com.lesconstructionssapete.stempyerp.core.domain.base.user.UserCredential;
import com.lesconstructionssapete.stempyerp.core.repository.base.user.UserRepository;

public class UserFacadeImpl implements UserFacade {

  private final UserRepository userRepository;

  public UserFacadeImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User validateCredentials(UserCredential userCredential) {

    return TransactionManager.execute(
        TransactionPropagation.REQUIRED,
        con -> {
          return userRepository.validateCredentials(con, userCredential);
        });

  }

  @Override
  public User findByUserNo(String userNo) {

    return TransactionManager.execute(
        TransactionPropagation.REQUIRED,
        con -> {
          return userRepository.findByUserNo(con, userNo);
        });

  }

}
