package com.lesconstructionssapete.stempyerp.app.http;

import java.time.Instant;

import com.lesconstructionssapete.stempyerp.core.domain.base.user.User;

public class RequestMetadata {

  private String requestId;
  private Instant timestamp;

  private User user;
  private String userNo;
  private String clientIp;
  private String source; // e.g., "POS", "API"

  private boolean dryRun;
  private boolean skipWarnings;

  private String idempotencyKey;

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }

  public String getUserNo() {
    return userNo;
  }

  public void setUserNo(String userNo) {
    this.userNo = userNo;
  }

  public String getClientIp() {
    return clientIp;
  }

  public void setClientIp(String clientIp) {
    this.clientIp = clientIp;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
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

  public String getIdempotencyKey() {
    return idempotencyKey;
  }

  public void setIdempotencyKey(String idempotencyKey) {
    this.idempotencyKey = idempotencyKey;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

}
