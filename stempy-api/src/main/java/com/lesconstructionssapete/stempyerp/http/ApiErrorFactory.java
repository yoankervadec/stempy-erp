package com.lesconstructionssapete.stempyerp.http;

import com.lesconstructionssapete.stempyerp.exception.ClientFacing;
import com.lesconstructionssapete.stempyerp.http.contract.ApiError;

/**
 * Factory class for creating ApiError instances from exceptions.
 */

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
