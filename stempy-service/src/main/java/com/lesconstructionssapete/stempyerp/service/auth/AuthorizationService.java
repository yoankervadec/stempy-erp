package com.lesconstructionssapete.stempyerp.service.auth;

// Used everywhere

import java.sql.Connection;

import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationAction;

public interface AuthorizationService {

  boolean has(Connection connection, long userId, String resource, ApplicationAction action);
}
