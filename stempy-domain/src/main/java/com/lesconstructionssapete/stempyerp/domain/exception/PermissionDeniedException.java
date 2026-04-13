package com.lesconstructionssapete.stempyerp.domain.exception;

import com.lesconstructionssapete.stempyerp.annotation.ApplicationAction;
import com.lesconstructionssapete.stempyerp.exception.DomainException;
import com.lesconstructionssapete.stempyerp.exception.ErrorCode;

public class PermissionDeniedException extends DomainException {

  public PermissionDeniedException(String resource, ApplicationAction action) {
    super(ErrorCode.PERMISSION_DENIED, "PERMISSION_DENIED",
        "You do not have the required permission to " + "'" + action + "'" + " resource " + "'" + resource + "'.");
  }
}
