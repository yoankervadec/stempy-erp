package com.lesconstructionssapete.stempyerp.domain.exception;

import com.lesconstructionssapete.stempyerp.annotation.AppAction;
import com.lesconstructionssapete.stempyerp.annotation.AppResource;
import com.lesconstructionssapete.stempyerp.exception.DomainException;
import com.lesconstructionssapete.stempyerp.exception.ErrorCode;

public class PermissionDeniedException extends DomainException {

  public PermissionDeniedException(AppResource resource, AppAction action) {
    super(ErrorCode.PERMISSION_DENIED, "PERMISSION_DENIED",
        "You do not have the required permission to " + "'" + action + "'" + " resource " + "'" + resource + "'.");
  }
}
