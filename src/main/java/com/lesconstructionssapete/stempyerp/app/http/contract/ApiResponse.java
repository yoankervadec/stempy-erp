package com.lesconstructionssapete.stempyerp.app.http.contract;

import java.util.List;

public class ApiResponse<T> {

  private final boolean success;
  private final String message;
  private final List<String> warnings;
  private final ApiError error;
  private final ResponseMetadata metadata;
  private final Pagination page;
  private final List<ResponseAction> actions;
  private final T data;

  public ApiResponse(
      boolean success,
      String message,
      ResponseMetadata metadata,
      List<ResponseAction> actions,
      Pagination page,
      List<String> warnings,
      ApiError error,
      T data) {
    this.success = success;
    this.message = message;
    this.metadata = metadata;
    this.actions = actions;
    this.page = page;
    this.warnings = warnings;
    this.error = error;
    this.data = data;
  }

  public static <T> ApiResponse<T> ofSuccess(
      String message,
      ResponseMetadata metadata,
      List<ResponseAction> actions,
      Pagination page,
      List<String> warnings,
      T data) {
    return new ApiResponse<>(
        true,
        message,
        metadata,
        actions,
        page,
        warnings,
        null,
        data);
  }

  public static <T> ApiResponse<T> ofFailure(
      String message,
      ResponseMetadata metadata,
      ApiError error) {
    return new ApiResponse<>(
        false,
        message,
        metadata,
        null,
        null,
        null,
        error,
        null);
  }

  public boolean isSuccess() {
    return success;
  }

  public String getMessage() {
    return message;
  }

  public List<String> getWarnings() {
    return warnings;
  }

  public ApiError getError() {
    return error;
  }

  public ResponseMetadata getMetadata() {
    return metadata;
  }

  public Pagination getPage() {
    return page;
  }

  public List<ResponseAction> getActions() {
    return actions;
  }

  public T getData() {
    return data;
  }

}
