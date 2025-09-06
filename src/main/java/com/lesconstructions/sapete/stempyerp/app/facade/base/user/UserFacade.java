package com.lesconstructions.sapete.stempyerp.app.facade.base.user;

import com.lesconstructions.sapete.stempyerp.core.domain.base.user.User;

public interface UserFacade {

  User validateCredentials(String usernameLong, String password);

}
