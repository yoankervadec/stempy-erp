package com.lesconstructionssapete.stempyerp.exception;

public class IllegalFieldException extends InfrastructureException {
  public IllegalFieldException(String message) {
    super(ErrorCode.ILLEGAL_FIELD.getCode(), message);
  }

}
