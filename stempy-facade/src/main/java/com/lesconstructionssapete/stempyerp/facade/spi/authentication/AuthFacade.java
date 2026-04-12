package com.lesconstructionssapete.stempyerp.facade.spi.authentication;

import com.lesconstructionssapete.stempyerp.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.auth.UserCredential;

public interface AuthFacade {

  AuthToken login(UserCredential userCredential);

  AuthToken refresh(String refreshToken);
}
