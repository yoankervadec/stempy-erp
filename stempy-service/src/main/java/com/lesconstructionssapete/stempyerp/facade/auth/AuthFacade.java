package com.lesconstructionssapete.stempyerp.facade.auth;

import com.lesconstructionssapete.stempyerp.domain.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.domain.auth.UserCredential;

public interface AuthFacade {

  AuthToken save(AuthToken token);

  AuthToken login(UserCredential userCredential);

  AuthToken refresh(String refreshToken);
}
