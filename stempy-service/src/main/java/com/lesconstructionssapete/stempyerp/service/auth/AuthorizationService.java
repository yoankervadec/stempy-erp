package com.lesconstructionssapete.stempyerp.service.auth;

import com.lesconstructionssapete.stempyerp.annotation.ApplicationAction;

public interface AuthorizationService {

  boolean has(long userId, String resource, ApplicationAction action);

}
