package com.lesconstructionssapete.stempyerp.exception;

public class SQLBindingException extends InfrastructureException {

  public SQLBindingException(String message, Throwable cause) {
    super("SQL_BINDING_ERROR", message, cause);
  }

}
