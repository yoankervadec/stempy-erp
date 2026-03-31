package com.lesconstructionssapete.stempyerp.facade.user;

import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.User;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;

public interface UserFacade {

  List<User> fetch(DomainQuery query);

}
