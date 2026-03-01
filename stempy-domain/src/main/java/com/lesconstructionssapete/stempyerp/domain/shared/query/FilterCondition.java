package com.lesconstructionssapete.stempyerp.domain.shared.query;

public record FilterCondition(
    String field,
    ComparisonOperator operator,
    Object value) implements FilterNode {

}
