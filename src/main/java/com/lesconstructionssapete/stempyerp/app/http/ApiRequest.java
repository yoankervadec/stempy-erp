package com.lesconstructionssapete.stempyerp.app.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.lesconstructionssapete.stempyerp.core.domain.base.user.User;

public class ApiRequest {

  private RequestMetadata metadata; // serves info about the request
  private User user; // authenticated user making the request
  private JsonNode payload; // request body payload

  public ApiRequest(RequestMetadata metadata, User user, JsonNode payload) {
    this.metadata = metadata;
    this.user = user;
    this.payload = payload;
  }

  public boolean hasPayload() {
    return payload != null && !payload.isNull();
  }

  public RequestMetadata getMetadata() {
    return metadata;
  }

  public void setMetadata(RequestMetadata metadata) {
    this.metadata = metadata;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
    this.metadata.setUser(user);
  }

  public JsonNode getPayload() {
    return payload;
  }

  public void setPayload(JsonNode payload) {
    this.payload = payload;
  }

}
