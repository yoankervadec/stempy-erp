package com.lesconstructionssapete.stempyerp.exception;

import com.lesconstructionssapete.stempyerp.exception.ErrorCode;

import io.javalin.http.HttpStatus;

public class AuthenticationException extends ApiException {

  public AuthenticationException(String code, String message) {
    super(ErrorCode.UNAUTHENTICATED, code, message, HttpStatus.UNAUTHORIZED);
  }

}
