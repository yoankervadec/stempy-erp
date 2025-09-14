package com.lesconstructionssapete.stempyerp.core.exception.api;

import com.lesconstructionssapete.stempyerp.core.exception.ApiException;
import com.lesconstructionssapete.stempyerp.core.exception.ErrorCode;

public class UserNotFoundException extends ApiException {

  public UserNotFoundException(String errorCode, String message) {
    super(
        message != null ? message : ErrorCode.NOT_FOUND.getDefaultMessage(),
        errorCode != null ? errorCode : ErrorCode.NOT_FOUND.getCode(),
        404);
  }
}
