package com.lesconstructionssapete.stempyerp.core.exception.api;

import com.lesconstructionssapete.stempyerp.core.exception.ApiException;
import com.lesconstructionssapete.stempyerp.core.exception.ErrorCode;

import io.javalin.http.HttpStatus;

public class InvalidBodyException extends ApiException {

  public InvalidBodyException(String code, String message) {
    super(ErrorCode.INVALID_BODY, code, message, HttpStatus.BAD_REQUEST);
  }

}
