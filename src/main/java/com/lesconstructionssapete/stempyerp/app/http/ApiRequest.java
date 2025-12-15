package com.lesconstructionssapete.stempyerp.app.http;

public class ApiRequest<T> {

  private RequestMetadata metadata;
  private T payload; // the actual data, can be nested products, customers, etc.

  public RequestMetadata getMetadata() {
    return metadata;
  }

  public void setMetadata(RequestMetadata metadata) {
    this.metadata = metadata;
  }

  public T getPayload() {
    return payload;
  }

  public void setPayload(T payload) {
    this.payload = payload;
  }

}
