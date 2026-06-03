package com.lesconstructionssapete.stempyerp.domain.query;

import com.lesconstructionssapete.stempyerp.domain.field.EntityField;

public record FilterCondition(
    EntityField field,
    ComparisonOperator operator,
    Object value) implements FilterNode {

}
