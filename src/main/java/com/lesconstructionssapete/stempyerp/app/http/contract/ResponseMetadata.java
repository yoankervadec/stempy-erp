package com.lesconstructionssapete.stempyerp.app.http.contract;

import java.time.Instant;

public class ResponseMetadata {

  private final Instant timestamp;
  private final String requestId;
  private final String path;

  public ResponseMetadata(String requestId, String path) {
    this.timestamp = Instant.now();
    this.requestId = requestId;
    this.path = path;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public String getRequestId() {
    return requestId;
  }

  public String getPath() {
    return path;
  }

}
