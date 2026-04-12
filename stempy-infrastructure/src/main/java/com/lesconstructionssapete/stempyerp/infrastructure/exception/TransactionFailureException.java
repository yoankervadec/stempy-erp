package com.lesconstructionssapete.stempyerp.infrastructure.exception;

import com.lesconstructionssapete.stempyerp.exception.ErrorCode;
import com.lesconstructionssapete.stempyerp.exception.InfrastructureException;

public class TransactionFailureException extends InfrastructureException {

  public TransactionFailureException(String message, Throwable cause) {
    super(ErrorCode.TRANSACTION_FAILURE.getCode(), message, cause);
  }
}
