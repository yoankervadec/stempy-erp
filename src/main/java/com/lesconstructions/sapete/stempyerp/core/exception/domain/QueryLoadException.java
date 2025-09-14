package com.lesconstructions.sapete.stempyerp.core.exception.domain;

import com.lesconstructions.sapete.stempyerp.core.exception.DomainException;
import com.lesconstructions.sapete.stempyerp.core.exception.ErrorCode;

public class QueryLoadException extends DomainException {

  public QueryLoadException(String path, Throwable cause) {
    super(ErrorCode.QUERY_LOAD_EXCEPTION.getDefaultMessage() + path, ErrorCode.QUERY_LOAD_EXCEPTION.getCode(), cause);
  }
}
