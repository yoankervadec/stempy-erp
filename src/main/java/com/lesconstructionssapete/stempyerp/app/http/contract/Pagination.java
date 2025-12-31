package com.lesconstructionssapete.stempyerp.app.http.contract;

public class Pagination {

  private final String type = "cursor";
  private final String next;
  private final boolean hasMore;
  private final Integer limit;

  public Pagination(String next, boolean hasMore, Integer limit) {
    this.next = next;
    this.hasMore = hasMore;
    this.limit = limit;
  }

  public String getType() {
    return type;
  }

  public String getNext() {
    return next;
  }

  public boolean isHasMore() {
    return hasMore;
  }

  public Integer getLimit() {
    return limit;
  }

}
