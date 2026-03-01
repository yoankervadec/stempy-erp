package com.lesconstructionssapete.stempyerp.app.http;

public enum BodyKey {

  OPTIONS("options"),
  QUERY("query"),
  PAYLOAD("payload");

  private final String key;

  BodyKey(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

}
