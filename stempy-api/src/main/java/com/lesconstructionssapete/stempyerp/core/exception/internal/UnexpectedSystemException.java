package com.lesconstructionssapete.stempyerp.core.exception.internal;

import com.lesconstructionssapete.stempyerp.core.exception.ErrorCode;
import com.lesconstructionssapete.stempyerp.core.exception.InternalException;

public class UnexpectedSystemException extends InternalException {

  public static final String CODE = "UNEXPECTED_ERROR";

  public UnexpectedSystemException(String message, Throwable cause) {
    super(ErrorCode.UNDEFINED_SYSTEM_ERROR, CODE, message, cause);
  }
}