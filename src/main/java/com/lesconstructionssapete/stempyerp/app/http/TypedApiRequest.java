package com.lesconstructionssapete.stempyerp.app.http;

import com.lesconstructionssapete.stempyerp.core.domain.base.user.User;

public final class TypedApiRequest<T> {

  private final ApiRequest base;
  private final T body;

  public TypedApiRequest(ApiRequest base, T body) {
    this.base = base;
    this.body = body;
  }

  public RequestMetadata getMetadata() {
    return base.getMetadata();
  }

  public User getUser() {
    return base.getUser();
  }

  public T getBody() {
    return body;
  }

}
