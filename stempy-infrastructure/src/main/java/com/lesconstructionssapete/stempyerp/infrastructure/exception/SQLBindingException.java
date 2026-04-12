package com.lesconstructionssapete.stempyerp.infrastructure.exception;

import com.lesconstructionssapete.stempyerp.exception.InfrastructureException;

public class SQLBindingException extends InfrastructureException {

  public SQLBindingException(String message, Throwable cause) {
    super("SQL_BINDING_ERROR", message, cause);
  }

}
