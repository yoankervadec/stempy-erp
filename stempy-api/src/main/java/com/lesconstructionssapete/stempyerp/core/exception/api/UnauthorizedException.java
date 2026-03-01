package com.lesconstructionssapete.stempyerp.core.exception.api;

import com.lesconstructionssapete.stempyerp.core.exception.ApiException;
import com.lesconstructionssapete.stempyerp.core.exception.ErrorCode;

import io.javalin.http.HttpStatus;

public class UnauthorizedException extends ApiException {

  public UnauthorizedException(String code, String message) {
    super(ErrorCode.UNAUTHORIZED, code, message, HttpStatus.UNAUTHORIZED);
  }

}
