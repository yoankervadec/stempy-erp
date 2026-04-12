package com.lesconstructionssapete.stempyerp.facade.impl.user;

import java.util.List;

import com.lesconstructionssapete.stempyerp.facade.spi.user.UserFacade;
import com.lesconstructionssapete.stempyerp.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.service.spi.user.UserService;
import com.lesconstructionssapete.stempyerp.transaction.TransactionPropagation;
import com.lesconstructionssapete.stempyerp.transaction.TransactionRunner;
import com.lesconstructionssapete.stempyerp.user.User;

public class UserFacadeImpl implements UserFacade {

  private final TransactionRunner transaction;
  private final UserService userService;

  public UserFacadeImpl(TransactionRunner transaction, UserService userService) {
    this.transaction = transaction;
    this.userService = userService;
  }

  @Override
  public List<User> fetch(DomainQuery query) {

    return transaction.execute(
        TransactionPropagation.REQUIRED,
        con -> {
          return userService.fetch(con, query);
        });
  }

}
