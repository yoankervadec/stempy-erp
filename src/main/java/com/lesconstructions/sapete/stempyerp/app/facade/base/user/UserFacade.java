package com.lesconstructions.sapete.stempyerp.app.facade.base.user;

import com.lesconstructions.sapete.stempyerp.core.domain.base.user.User;
import com.lesconstructions.sapete.stempyerp.core.domain.base.user.UserCredential;

public interface UserFacade {

  User validateCredentials(UserCredential userCredential);

  User findByUserNo(String userNo);

}
