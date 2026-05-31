package com.lesconstructionssapete.stempyerp.domain.query;

import com.lesconstructionssapete.stempyerp.domain.field.DomainFieldProvider;

public record SortSpec(
    DomainFieldProvider field,
    boolean ascending) {

}
