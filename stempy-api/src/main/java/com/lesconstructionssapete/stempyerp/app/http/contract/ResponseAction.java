package com.lesconstructionssapete.stempyerp.app.http.contract;

public class ResponseAction {

  private final String rel;
  private final String method;
  private final String href;

  public ResponseAction(String rel, String method, String href) {
    this.rel = rel;
    this.method = method;
    this.href = href;
  }

  public String getRel() {
    return rel;
  }

  public String getMethod() {
    return method.toUpperCase();
  }

  public String getHref() {
    return href.toLowerCase();
  }

}
