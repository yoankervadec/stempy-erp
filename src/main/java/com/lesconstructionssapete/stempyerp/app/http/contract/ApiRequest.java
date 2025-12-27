package com.lesconstructionssapete.stempyerp.app.http.contract;

import com.fasterxml.jackson.databind.JsonNode;
import com.lesconstructionssapete.stempyerp.core.domain.base.user.User;

public class ApiRequest {

  private RequestContext context;
  private RequestOptions options;
  private RequestQuery query;
  private JsonNode body;

  public ApiRequest(JsonNode body) {
    this.body = body;
  }

  public boolean hasBody() {
    return body != null && !body.isNull();
  }

  public void setContextUser(User user) {
    if (this.context != null) {
      this.context.setUser(user);
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
