package com.lesconstructionssapete.stempyerp.exception;

import io.javalin.http.HttpStatus;

public class UnauthorizedException extends ApiException {

  public UnauthorizedException(String code, String message) {
    super(ErrorCode.UNAUTHORIZED, code, message, HttpStatus.UNAUTHORIZED);
  }

}
