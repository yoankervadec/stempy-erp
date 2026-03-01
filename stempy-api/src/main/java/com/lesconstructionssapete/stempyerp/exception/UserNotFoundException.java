package com.lesconstructionssapete.stempyerp.exception;

import com.lesconstructionssapete.stempyerp.exception.ErrorCode;

import io.javalin.http.HttpStatus;

public class UserNotFoundException extends ApiException {

  public UserNotFoundException(String code, String message) {
    super(ErrorCode.NOT_FOUND, code, message, HttpStatus.NOT_FOUND);
  }
}
