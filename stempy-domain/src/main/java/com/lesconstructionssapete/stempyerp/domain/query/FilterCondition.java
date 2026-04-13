package com.lesconstructionssapete.stempyerp.domain.query;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;

public record FilterCondition(
    DomainField field,
    ComparisonOperator operator,
    Object value) implements FilterNode {

}
