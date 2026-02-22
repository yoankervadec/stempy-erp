package com.lesconstructionssapete.stempyerp.app.http.query;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.lesconstructionssapete.stempyerp.app.http.contract.RequestQuery;
import com.lesconstructionssapete.stempyerp.core.domain.shared.query.ComparisonOperator;
import com.lesconstructionssapete.stempyerp.core.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.core.domain.shared.query.FilterCondition;
import com.lesconstructionssapete.stempyerp.core.domain.shared.query.FilterGroup;
import com.lesconstructionssapete.stempyerp.core.domain.shared.query.FilterNode;
import com.lesconstructionssapete.stempyerp.core.domain.shared.query.LogicalOperator;
import com.lesconstructionssapete.stempyerp.core.domain.shared.query.PageSpec;
import com.lesconstructionssapete.stempyerp.core.domain.shared.query.SortSpec;

public final class RequestQueryMapper {

  private RequestQueryMapper() {
    // Utility class
  }

  public static DomainQuery map(RequestQuery requestQuery) {

    FilterNode filters = parseFilters(requestQuery.getFilters());
    List<SortSpec> sorting = parseSorting(requestQuery.getSorting());
    PageSpec pagination = parsePagination(requestQuery.getPagination());

    return new DomainQuery(filters, sorting, pagination);
  }

  // ------------------------
  // FILTER PARSING
  // ------------------------

  private static FilterNode parseFilters(JsonNode node) {
    if (node == null || node.isNull()) {
      return null;
    }

    if (node.has("operator")) {
      LogicalOperator operator = LogicalOperator.valueOf(node.get("operator").asText());

      List<FilterNode> children = new ArrayList<>();
      for (JsonNode child : node.get("children")) {
        children.add(parseFilters(child));
      }

      return new FilterGroup(operator, children);
    }

    return new FilterCondition(
        node.get("field").asText(),
        ComparisonOperator.valueOf(node.get("comparison").asText()),
        extractValue(node.get("value")));
  }

  private static Object extractValue(JsonNode valueNode) {
    if (valueNode == null || valueNode.isNull())
      return null;

    if (valueNode.isTextual())
      return valueNode.asText();
    if (valueNode.isInt())
      return valueNode.asInt();
    if (valueNode.isLong())
      return valueNode.asLong();
    if (valueNode.isBoolean())
      return valueNode.asBoolean();
    if (valueNode.isDouble())
      return valueNode.asDouble();

    return valueNode;
  }

  // ------------------------
  // SORTING
  // ------------------------

  private static List<SortSpec> parseSorting(JsonNode node) {
    if (node == null || node.isNull())
      return List.of();

    List<SortSpec> sorts = new ArrayList<>();
    for (JsonNode s : node) {
      sorts.add(new SortSpec(
          s.get("field").asText(),
          s.get("direction").asText().equalsIgnoreCase("ASC")));
    }

    return sorts;
  }

  // ------------------------
  // PAGINATION
  // ------------------------

  private static PageSpec parsePagination(JsonNode node) {
    if (node == null || node.isNull())
      return null;

    return new PageSpec(
        node.get("page").asInt(),
        node.get("size").asInt());
  }

}
