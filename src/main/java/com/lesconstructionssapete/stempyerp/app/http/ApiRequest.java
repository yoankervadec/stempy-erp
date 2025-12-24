package com.lesconstructionssapete.stempyerp.app.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.lesconstructionssapete.stempyerp.core.domain.base.user.User;

public class ApiRequest {

  private final ServerContext context;
  private RequestOptions options;
  private RequestQuery query;
  private JsonNode payload; // request body payload

  public ApiRequest(ServerContext context, JsonNode payload) {
    this.context = context;
    this.payload = payload;
  }

  public boolean hasPayload() {
    return payload != null && !payload.isNull();
  }

  public void setContextUser(User user) {
    this.context.setUser(user);
  }

  public ServerContext getContext() {
    return context;
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
