package com.lesconstructionssapete.stempyerp.query;

import com.lesconstructionssapete.stempyerp.field.DomainField;

public record SortSpec(
    DomainField field,
    boolean ascending) {

}
