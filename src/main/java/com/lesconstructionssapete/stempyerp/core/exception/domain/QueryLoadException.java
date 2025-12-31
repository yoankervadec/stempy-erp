package com.lesconstructionssapete.stempyerp.core.exception.domain;

import com.lesconstructionssapete.stempyerp.core.exception.DomainException;
import com.lesconstructionssapete.stempyerp.core.exception.ErrorCode;

public class QueryLoadException extends DomainException {

  public QueryLoadException(String path, Throwable cause) {
    super(
        ErrorCode.QUERY_LOAD_EXCEPTION,
        ErrorCode.QUERY_LOAD_EXCEPTION.getCode(),
        ErrorCode.QUERY_LOAD_EXCEPTION.getDefaultMessage() + path,
        cause);
  }
}
