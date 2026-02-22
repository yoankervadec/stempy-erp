package com.lesconstructionssapete.stempyerp.core.exception.domain;

import com.lesconstructionssapete.stempyerp.core.exception.DomainException;
import com.lesconstructionssapete.stempyerp.core.exception.ErrorCode;

public class FieldNotFoundException extends DomainException {

  public static final String CODE = "FIELD_NOT_FOUND";

  public FieldNotFoundException(String message) {
    super(ErrorCode.UNDEFINED_DOMAIN_EXCEPTION, CODE, message);
  }

}
