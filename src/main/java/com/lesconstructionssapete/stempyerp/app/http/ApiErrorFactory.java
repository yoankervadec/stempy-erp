package com.lesconstructionssapete.stempyerp.app.http;

import com.lesconstructionssapete.stempyerp.app.http.contract.ApiError;
import com.lesconstructionssapete.stempyerp.core.exception.ClientFacing;

public final class ApiErrorFactory {

  private ApiErrorFactory() {
    // Prevent instantiation
  }

  public static ApiError from(ClientFacing e) {
    String message = e.getMessage();
    String description = e.getDescription();

    if (e.exposeCause() && e.getCause() != null) {
      message += " Caused by: " + e.getCause().getMessage();
      description = e.getCause().getMessage();
    }

    return new ApiError(e.getCode(), message, description);
  }

}
