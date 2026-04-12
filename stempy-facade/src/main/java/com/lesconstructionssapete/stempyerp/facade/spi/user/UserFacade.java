package com.lesconstructionssapete.stempyerp.facade.spi.user;

import java.util.List;

import com.lesconstructionssapete.stempyerp.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.user.User;

public interface UserFacade {

  List<User> fetch(DomainQuery query);

}
