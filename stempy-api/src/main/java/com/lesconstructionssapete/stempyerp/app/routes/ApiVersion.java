package com.lesconstructionssapete.stempyerp.app.routes;

public enum ApiVersion {

  V1("v1");

  private final String value;

  ApiVersion(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }

}
