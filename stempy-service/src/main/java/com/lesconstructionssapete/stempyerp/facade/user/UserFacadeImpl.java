package com.lesconstructionssapete.stempyerp.facade.user;

import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.User;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.repository.UserRepository;
import com.lesconstructionssapete.stempyerp.transaction.TransactionPropagation;
import com.lesconstructionssapete.stempyerp.transaction.TransactionRunner;

public class UserFacadeImpl implements UserFacade {

  private final TransactionRunner transaction;
  private final UserRepository userRepository;

  public UserFacadeImpl(TransactionRunner transaction, UserRepository userRepository) {
    this.transaction = transaction;
    this.userRepository = userRepository;
  }

  @Override
  public List<User> fetch(DomainQuery query) {

    return transaction.execute(
        TransactionPropagation.REQUIRED,
        con -> {
          return userRepository.fetch(con, query);
        });
  }

}
