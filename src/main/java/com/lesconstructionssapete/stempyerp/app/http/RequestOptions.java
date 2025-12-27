package com.lesconstructionssapete.stempyerp.app.http;

import java.util.Map;

public class RequestOptions {

  private String idempotencyKey;
  private String userAgent;
  private boolean dryRun;
  private boolean skipWarnings;

  private Map<String, Object> flags;

  public String getIdempotencyKey() {
    return idempotencyKey;
  }

  public void setIdempotencyKey(String idempotencyKey) {
    this.idempotencyKey = idempotencyKey;
  }

  public String getUserAgent() {
    return userAgent;
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  public boolean isDryRun() {
    return dryRun;
  }

  public void setDryRun(boolean dryRun) {
    this.dryRun = dryRun;
  }

  public boolean isSkipWarnings() {
    return skipWarnings;
  }

  public void setSkipWarnings(boolean skipWarnings) {
    this.skipWarnings = skipWarnings;
  }

  public Map<String, Object> getFlags() {
    return flags;
  }

  public void setFlags(Map<String, Object> flags) {
    this.flags = flags;
  }

}