package com.lesconstructionssapete.stempyerp.app.http;

public final class TypedApiRequest<T> {

  private final ApiRequest base;
  private final T body;

  public TypedApiRequest(ApiRequest base, T body) {
    this.base = base;
    this.body = body;
  }

  public ApiRequest getBase() {
    return base;
  }

  public T getBody() {
    return body;
  }

}
