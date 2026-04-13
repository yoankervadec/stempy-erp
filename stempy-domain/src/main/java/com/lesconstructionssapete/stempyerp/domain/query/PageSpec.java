package com.lesconstructionssapete.stempyerp.domain.query;

public record PageSpec(
    int page,
    int size) {
  public int offset() {
    return page * size;
  }
}
