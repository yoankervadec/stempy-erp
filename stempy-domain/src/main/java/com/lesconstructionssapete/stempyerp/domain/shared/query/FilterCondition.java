package com.lesconstructionssapete.stempyerp.domain.shared.query;

import com.lesconstructionssapete.stempyerp.field.DomainField;

public record FilterCondition(
    DomainField field,
    ComparisonOperator operator,
    Object value) implements FilterNode {

}
