package com.lesconstructionssapete.stempyerp.app.http.contract;

public final class ApiError {

  private final String code;
  private final String message;
  private final String description;

  public ApiError(String code, String message, String description) {
    this.code = code;
    this.message = message;
    this.description = description;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public String getDescription() {
    return description;
  }

}
