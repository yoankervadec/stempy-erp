package com.lesconstructionssapete.stempyerp.exception;

public class QueryLoadException extends DomainException {

  public QueryLoadException(String path, Throwable cause) {
    super(
        ErrorCode.QUERY_LOAD_EXCEPTION,
        ErrorCode.QUERY_LOAD_EXCEPTION.getCode(),
        ErrorCode.QUERY_LOAD_EXCEPTION.getDefaultMessage() + path,
        cause);
  }
}
