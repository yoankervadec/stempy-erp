package com.lesconstructionssapete.stempyerp.facade.user;

import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.user.User;

public interface UserFacade {

  List<User> fetch(DomainQuery query);

}
