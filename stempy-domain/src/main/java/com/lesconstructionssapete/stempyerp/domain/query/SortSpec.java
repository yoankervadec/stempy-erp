package com.lesconstructionssapete.stempyerp.domain.query;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;

public record SortSpec(
    DomainField field,
    boolean ascending) {

}
