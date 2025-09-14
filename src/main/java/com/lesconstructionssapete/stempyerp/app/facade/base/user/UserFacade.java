package com.lesconstructionssapete.stempyerp.app.facade.base.user;

import com.lesconstructionssapete.stempyerp.core.domain.base.user.User;
import com.lesconstructionssapete.stempyerp.core.domain.base.user.UserCredential;

public interface UserFacade {

  User validateCredentials(UserCredential userCredential);

  User findByUserNo(String userNo);

}
