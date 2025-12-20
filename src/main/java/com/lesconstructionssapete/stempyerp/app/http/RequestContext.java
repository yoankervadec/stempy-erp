package com.lesconstructionssapete.stempyerp.app.http;

import com.lesconstructionssapete.stempyerp.core.domain.base.user.User;

/**
 * Holds context information about the current request, including metadata and
 * user details.
 * 
 * Lives for the entire lifecycle of a request.
 * 
 * Middleware builds it, Controllers consume it, Response uses it
 */

public class RequestContext {

  private final RequestMetadata metadata;
  private final User user;

  private ApiRequest<?> apiRequest;

  public RequestContext(RequestMetadata metadata, User user) {
    this.metadata = metadata;
    this.user = user;
  }

  public RequestMetadata getMetadata() {
    return metadata;
  }

  public User getUser() {
    return user;
  }

  public void setApiRequest(ApiRequest<?> apiRequest) {
    this.apiRequest = apiRequest;
  }

  @SuppressWarnings("unchecked")
  public <T> ApiRequest<T> getApiRequest() {
    return (ApiRequest<T>) apiRequest;
  }

}
