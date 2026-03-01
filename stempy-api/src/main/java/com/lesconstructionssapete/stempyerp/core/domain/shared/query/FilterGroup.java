package com.lesconstructionssapete.stempyerp.core.domain.shared.query;

import java.util.List;

public record FilterGroup(
  LogicalOperator operator,
  List<FilterNode> children
) implements FilterNode {

}
