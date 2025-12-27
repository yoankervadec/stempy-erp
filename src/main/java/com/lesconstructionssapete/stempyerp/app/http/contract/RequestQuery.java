package com.lesconstructionssapete.stempyerp.app.http.contract;

import com.fasterxml.jackson.databind.JsonNode;

public class RequestQuery {

  private JsonNode filters; // complex AND/OR trees
  private JsonNode sorting;
  private JsonNode pagination;

  private JsonNode preferences; // column visibility, density, etc.

  public JsonNode getFilters() {
    return filters;
  }

  public void setFilters(JsonNode filters) {
    this.filters = filters;
  }

  public JsonNode getSorting() {
    return sorting;
  }

  public void setSorting(JsonNode sorting) {
    this.sorting = sorting;
  }

  public JsonNode getPagination() {
    return pagination;
  }

  public void setPagination(JsonNode pagination) {
    this.pagination = pagination;
  }

  public JsonNode getPreferences() {
    return preferences;
  }

  public void setPreferences(JsonNode preferences) {
    this.preferences = preferences;
  }

}
