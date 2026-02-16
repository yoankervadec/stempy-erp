package com.lesconstructionssapete.stempyerp.app.facade.base.auth;

import com.lesconstructionssapete.stempyerp.core.config.db.TransactionManager;
import com.lesconstructionssapete.stempyerp.core.config.db.TransactionPropagation;
import com.lesconstructionssapete.stempyerp.core.domain.base.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.core.repository.base.auth.RefreshTokenRepository;

public class AuthFacadeImpl implements AuthFacade {

  private final RefreshTokenRepository refreshTokenRepository;

  public AuthFacadeImpl(RefreshTokenRepository refreshTokenRepository) {
    this.refreshTokenRepository = refreshTokenRepository;
  }

  @Override
  public boolean exists(String userNo, String refreshToken) {

    return TransactionManager.execute(
        TransactionPropagation.REQUIRED,
        con -> {
          return refreshTokenRepository.exists(con, userNo, refreshToken);
        });

  }

  @Override
  public AuthToken save(AuthToken token) {

    return TransactionManager.execute(
        TransactionPropagation.REQUIRED,
        con -> {
          refreshTokenRepository.save(con, token);
          return token;
        });
  }
}
