package com.lesconstructionssapete.stempyerp.service.spi.authorization;

import com.lesconstructionssapete.stempyerp.annotation.AppAction;
import com.lesconstructionssapete.stempyerp.annotation.AppResource;

public interface AuthorizationService {

  boolean has(long userId, AppResource resource, AppAction action);

}
