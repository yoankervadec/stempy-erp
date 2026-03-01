package com.lesconstructionssapete.stempyerp.domain.shared.query;

import java.util.List;

public record DomainQuery(
    FilterNode filters,
    List<SortSpec> sortSpec,
    PageSpec pageSpec) {

}
