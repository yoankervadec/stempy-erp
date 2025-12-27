package com.lesconstructionssapete.stempyerp.app.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.lesconstructionssapete.stempyerp.core.domain.base.user.User;

public class ApiRequest {

  private ServerContext context;
  private RequestOptions options;
  private RequestQuery query;
  private JsonNode payload; // request body payload

  public ApiRequest(JsonNode payload) {
    this.payload = payload;
  }

  public boolean hasPayload() {
    return payload != null && !payload.isNull();
  }

  public void setContextUser(User user) {
    if (this.context != null) {
      this.context.setUser(user);
    }
  }

  public ServerContext getContext() {
    return context;
  }

  public void setContext(ServerContext context) {
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

  public JsonNode getPayload() {
    return payload;
  }

  public void setPayload(JsonNode payload) {
    this.payload = payload;
  }

}
