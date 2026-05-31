package com.lesconstructionssapete.stempyerp.domain.query;

import com.lesconstructionssapete.stempyerp.domain.field.DomainFieldProvider;

public record FilterCondition(
    DomainFieldProvider field,
    ComparisonOperator operator,
    Object value) implements FilterNode {

}
