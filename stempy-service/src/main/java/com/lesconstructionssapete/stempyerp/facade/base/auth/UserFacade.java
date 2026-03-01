package com.lesconstructionssapete.stempyerp.facade.base.auth;

import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.base.auth.User;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;

public interface UserFacade {

  List<User> fetch(DomainQuery query);

}
