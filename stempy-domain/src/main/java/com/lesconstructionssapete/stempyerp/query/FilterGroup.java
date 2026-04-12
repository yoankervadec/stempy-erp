package com.lesconstructionssapete.stempyerp.query;

import java.util.List;

public record FilterGroup(
    LogicalOperator operator,
    List<FilterNode> children) implements FilterNode {

}
