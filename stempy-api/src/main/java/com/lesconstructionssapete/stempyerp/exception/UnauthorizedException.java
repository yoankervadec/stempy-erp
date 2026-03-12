package com.lesconstructionssapete.stempyerp.exception;

public class UnauthorizedException extends DomainException {

  public UnauthorizedException(String code, String message) {
    super(ErrorCode.UNAUTHORIZED, code, message);
  }

}
