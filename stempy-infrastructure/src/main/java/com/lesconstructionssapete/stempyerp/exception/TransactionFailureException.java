package com.lesconstructionssapete.stempyerp.exception;

public class TransactionFailureException extends InternalException {

  public static final String CODE = "TX_FAILURE";

  public TransactionFailureException(String message, Throwable cause) {
    super(ErrorCode.DATABASE_ERROR, CODE, message, cause);
  }
}
