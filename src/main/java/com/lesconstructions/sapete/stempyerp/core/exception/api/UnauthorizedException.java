package com.lesconstructions.sapete.stempyerp.core.exception.api;

import com.lesconstructions.sapete.stempyerp.core.exception.ApiException;
import com.lesconstructions.sapete.stempyerp.core.exception.ErrorCode;

public class UnauthorizedException extends ApiException {

  public UnauthorizedException(String errorCode, String message) {
    super(
        message != null ? message : ErrorCode.UNAUTHORIZED.getDefaultMessage(),
        errorCode != null ? errorCode : ErrorCode.UNAUTHORIZED.getCode(),
        401);
  }

}
