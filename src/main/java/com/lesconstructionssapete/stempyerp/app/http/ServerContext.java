package com.lesconstructionssapete.stempyerp.app.http;

import java.time.Instant;

import com.lesconstructionssapete.stempyerp.core.domain.base.user.User;

public final class ServerContext {

  private final String requestId;
  private final Instant timestamp;

  private User user;
  private final String userNo;
  private final String clientIp;

  public ServerContext(
      String requestId,
      Instant timestamp,
      String userNo,
      String clientIp,
      String source) {
    this.requestId = requestId;
    this.timestamp = timestamp;
    this.userNo = userNo;
    this.clientIp = clientIp;
  }

  public String getRequestId() {
    return requestId;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getUserNo() {
    return userNo;
  }

  public String getClientIp() {
    return clientIp;
  }

}
