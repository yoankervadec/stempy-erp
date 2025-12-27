package com.lesconstructionssapete.stempyerp.app.http.contract;

public class ApiResponse<T> {

  private final boolean success;
  private final String message;
  private final ResponseMetadata metadata;
  private final T data;

  public ApiResponse(boolean success, String message, ResponseMetadata metadata, T data) {
    this.success = success;
    this.message = message;
    this.metadata = metadata;
    this.data = data;
  }

  public static <T> ApiResponse<T> ofSuccess(String message, ResponseMetadata metadata, T data) {
    return new ApiResponse<>(true, message, metadata, data);
  }

  public static <T> ApiResponse<T> ofFailure(String message, ResponseMetadata metadata) {
    return new ApiResponse<>(false, message, metadata, null);
  }

  public boolean isSuccess() {
    return success;
  }

  public String getMessage() {
    return message;
  }

  public ResponseMetadata getMetadata() {
    return metadata;
  }

  public T getData() {
    return data;
  }

}
