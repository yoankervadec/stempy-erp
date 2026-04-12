package com.lesconstructionssapete.stempyerp.query;

public record PageSpec(
    int page,
    int size) {
  public int offset() {
    return page * size;
  }
}
