package com.lesconstructionssapete.stempyerp.app.http;

import java.util.Map;

public class RequestOptions {

  private String idempotencyKey;

  private Map<String, Object> flags;

  public String getIdempotencyKey() {
    return idempotencyKey;
  }

  public void setIdempotencyKey(String idempotencyKey) {
    this.idempotencyKey = idempotencyKey;
  }

  public Map<String, Object> getFlags() {
    return flags;
  }

  public void setFlags(Map<String, Object> flags) {
    this.flags = flags;
  }

}