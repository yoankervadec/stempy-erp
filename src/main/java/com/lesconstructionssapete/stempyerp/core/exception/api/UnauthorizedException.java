package com.lesconstructionssapete.stempyerp.core.exception.api;

import com.lesconstructionssapete.stempyerp.core.exception.ApiException;
import com.lesconstructionssapete.stempyerp.core.exception.ErrorCode;

public class UnauthorizedException extends ApiException {

  public UnauthorizedException(String errorCode, String message) {
    super(
        message != null ? message : ErrorCode.UNAUTHORIZED.getDefaultMessage(),
        errorCode != null ? errorCode : ErrorCode.UNAUTHORIZED.getCode(),
        401);
  }

}
