package com.lesconstructionssapete.stempyerp.http;

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
