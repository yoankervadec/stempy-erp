package com.lesconstructionssapete.stempyerp.app.http.contract;

import com.fasterxml.jackson.databind.JsonNode;
import com.lesconstructionssapete.stempyerp.core.domain.base.user.User;

/**
 * API Request contract:
 * - Encapsulates request context, options, query params, and JSON body
 * - Provides helper methods for common patterns (e.g. hasBody)
 * - Designed for flexible mapping from HTTP requests to service layer
 */
public class ApiRequest {

  private RequestContext context;
  private RequestOptions options;
  private RequestQuery query;
  private JsonNode body;

  public ApiRequest(JsonNode body) {
    this.body = body;
  }

  public ApiRequest() {
    // Default constructor
  }

  public boolean hasBody() {
    return body != null && !body.isNull();
  }

  public void setContextUser(User user) {
    if (this.context != null) {
      this.context.setUser(user);
    } else {
      throw new IllegalStateException("RequestContext must be set before setting user");
    }
  }

  public RequestContext getContext() {
    return context;
  }

  public void setContext(RequestContext context) {
    this.context = context;
  }

  public RequestOptions getOptions() {
    return options;
  }

  public void setOptions(RequestOptions options) {
    this.options = options;
  }

  public RequestQuery getQuery() {
    return query;
  }

  public void setQuery(RequestQuery query) {
    this.query = query;
  }

  public JsonNode getBody() {
    return body;
  }

  public void setBody(JsonNode payload) {
    this.body = payload;
  }

}
