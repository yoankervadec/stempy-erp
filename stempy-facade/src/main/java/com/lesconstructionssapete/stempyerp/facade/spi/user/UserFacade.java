package com.lesconstructionssapete.stempyerp.facade.spi.user;

import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.user.User;

public interface UserFacade {

  List<User> fetch(DomainQuery query);

}
