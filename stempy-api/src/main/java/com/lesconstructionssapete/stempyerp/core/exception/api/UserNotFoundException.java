package com.lesconstructionssapete.stempyerp.core.exception.api;

import com.lesconstructionssapete.stempyerp.core.exception.ApiException;
import com.lesconstructionssapete.stempyerp.core.exception.ErrorCode;

import io.javalin.http.HttpStatus;

public class UserNotFoundException extends ApiException {

  public UserNotFoundException(String code, String message) {
    super(ErrorCode.NOT_FOUND, code, message, HttpStatus.NOT_FOUND);
  }
}
