package com.lesconstructionssapete.stempyerp.app.http;

public enum Body {

  OPTIONS("options"),
  QUERY("query"),
  PAYLOAD("payload");

  private final String key;

  Body(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

}
