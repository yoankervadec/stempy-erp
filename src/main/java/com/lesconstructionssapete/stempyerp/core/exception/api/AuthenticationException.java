package com.lesconstructionssapete.stempyerp.core.exception.api;

import com.lesconstructionssapete.stempyerp.core.exception.ApiException;
import com.lesconstructionssapete.stempyerp.core.exception.ErrorCode;

import io.javalin.http.HttpStatus;

public class AuthenticationException extends ApiException {

  public AuthenticationException(String code, String message) {
    super(ErrorCode.UNAUTHENTICATED, code, message, HttpStatus.UNAUTHORIZED);
  }

}
