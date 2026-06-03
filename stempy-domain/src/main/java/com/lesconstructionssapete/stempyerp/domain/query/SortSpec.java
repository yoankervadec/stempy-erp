package com.lesconstructionssapete.stempyerp.domain.query;

import com.lesconstructionssapete.stempyerp.domain.field.EntityField;

public record SortSpec(
    EntityField field,
    boolean ascending) {

}
