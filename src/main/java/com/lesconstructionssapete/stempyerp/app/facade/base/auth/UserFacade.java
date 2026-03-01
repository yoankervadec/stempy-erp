package com.lesconstructionssapete.stempyerp.app.facade.base.auth;

import java.util.List;

import com.lesconstructionssapete.stempyerp.core.domain.base.auth.User;
import com.lesconstructionssapete.stempyerp.core.domain.shared.query.DomainQuery;

public interface UserFacade {

  List<User> fetch(DomainQuery query);

}
