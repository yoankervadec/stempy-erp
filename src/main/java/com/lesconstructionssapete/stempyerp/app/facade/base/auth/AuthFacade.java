package com.lesconstructionssapete.stempyerp.app.facade.base.auth;

import com.lesconstructionssapete.stempyerp.core.domain.base.auth.AuthToken;

public interface AuthFacade {

  boolean exists(String userNo, String refreshToken);

  AuthToken save(AuthToken token);
}
