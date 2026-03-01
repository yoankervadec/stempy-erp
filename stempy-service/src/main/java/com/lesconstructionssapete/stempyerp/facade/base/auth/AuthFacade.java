package com.lesconstructionssapete.stempyerp.facade.base.auth;

import com.lesconstructionssapete.stempyerp.domain.base.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.domain.base.auth.UserCredential;

public interface AuthFacade {

  boolean exists(String userNo, String refreshToken);

  AuthToken save(AuthToken token);

  AuthToken login(UserCredential userCredential);

  AuthToken refresh(String refreshToken);
}
