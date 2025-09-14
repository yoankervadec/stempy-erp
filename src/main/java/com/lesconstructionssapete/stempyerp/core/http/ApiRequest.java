package com.lesconstructionssapete.stempyerp.core.http;

import java.util.Map;

public class ApiRequest<T> {

  private Map<String, Object> metadata; // e.g., requestId, timestamps, client info
  private T payload; // the actual data, can be nested products, customers, etc.

  public Map<String, Object> getMetadata() {
    return metadata;
  }

  public void setMetadata(Map<String, Object> metadata) {
    this.metadata = metadata;
  }

  public T getPayload() {
    return payload;
  }

  public void setPayload(T payload) {
    this.payload = payload;
  }

}
