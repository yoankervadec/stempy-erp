package com.lesconstructionssapete.stempyerp.core.exception.api;

import com.lesconstructionssapete.stempyerp.core.exception.ApiException;
import com.lesconstructionssapete.stempyerp.core.exception.ErrorCode;

public class InvalidBodyException extends ApiException {

  public InvalidBodyException(String errorCode, String message) {
    super(
        message != null ? message : ErrorCode.INVALID_BODY.getDefaultMessage(),
        errorCode != null ? errorCode : ErrorCode.INVALID_BODY.getCode(),
        400);
  }

}
