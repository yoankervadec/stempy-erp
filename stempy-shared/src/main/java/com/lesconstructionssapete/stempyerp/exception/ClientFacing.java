package com.lesconstructionssapete.stempyerp.exception;

public interface ClientFacing {

  String getCode();

  String getMessage();

  String getDescription();

  default boolean exposeCause() {
    return false;
  }

  Throwable getCause();

}
