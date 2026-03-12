package com.lesconstructionssapete.stempyerp.exception;

public class TransactionFailureException extends InfrastructureException {

  public TransactionFailureException(String message, Throwable cause) {
    super(ErrorCode.TRANSACTION_FAILURE.getCode(), message, cause);
  }
}
