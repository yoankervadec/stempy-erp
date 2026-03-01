package com.lesconstructionssapete.stempyerp.exception;

import io.javalin.http.HttpStatus;

public class InvalidBodyException extends ApiException {

  public InvalidBodyException(String code, String message) {
    super(ErrorCode.INVALID_BODY, code, message, HttpStatus.BAD_REQUEST);
  }

}
