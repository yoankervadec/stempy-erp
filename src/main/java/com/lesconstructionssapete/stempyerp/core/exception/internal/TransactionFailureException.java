package com.lesconstructionssapete.stempyerp.core.exception.internal;

import com.lesconstructionssapete.stempyerp.core.exception.ErrorCode;
import com.lesconstructionssapete.stempyerp.core.exception.InternalException;

public class TransactionFailureException extends InternalException {

  public static final String CODE = "TX_FAILURE";

  public TransactionFailureException(String message, Throwable cause) {
    super(ErrorCode.DATABASE_ERROR, CODE, message, cause);
  }
}
