package com.lesconstructions.sapete.stempyerp.app.facade.base.auth;

import com.lesconstructions.sapete.stempyerp.core.domain.base.auth.AuthToken;

public interface AuthFacade {

  boolean exists(String userNo, String refreshToken);

  AuthToken save(AuthToken token);
}
