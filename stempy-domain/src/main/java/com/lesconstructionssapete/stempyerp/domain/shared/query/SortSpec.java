package com.lesconstructionssapete.stempyerp.domain.shared.query;

import com.lesconstructionssapete.stempyerp.field.DomainField;

public record SortSpec(
    DomainField field,
    boolean ascending) {

}
