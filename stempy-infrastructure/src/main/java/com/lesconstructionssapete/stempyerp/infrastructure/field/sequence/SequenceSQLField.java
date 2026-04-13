package com.lesconstructionssapete.stempyerp.infrastructure.field.sequence;

import java.sql.Types;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;
import com.lesconstructionssapete.stempyerp.domain.field.sequence.SequenceField;
import com.lesconstructionssapete.stempyerp.infrastructure.field.SQLField;

public final class SequenceSQLField {

  private SequenceSQLField() {
  }

  public static final String CORE_DOMAIN_ENTITY_SEQUENCE = "core_domain_entity_sequence";

  private static final SQLField DOMAIN_ENTITY_ID = new SQLField(
      SequenceField.DOMAIN_ENTITY_ID,
      CORE_DOMAIN_ENTITY_SEQUENCE,
      "domain_entity_id",
      Types.BIGINT);

  private static final SQLField NEXT = new SQLField(
      SequenceField.NEXT,
      CORE_DOMAIN_ENTITY_SEQUENCE,
      "next",
      Types.BIGINT);

  private static final SQLField ENABLED = new SQLField(
      SequenceField.ENABLED,
      CORE_DOMAIN_ENTITY_SEQUENCE,
      "enabled",
      Types.BOOLEAN);

  private static final SQLField CREATED_AT = new SQLField(
      SequenceField.CREATED_AT,
      CORE_DOMAIN_ENTITY_SEQUENCE,
      "created_at",
      Types.TIMESTAMP);

  private static final Map<DomainField, SQLField> LOOKUP = Map.ofEntries(
      Map.entry(SequenceField.DOMAIN_ENTITY_ID, DOMAIN_ENTITY_ID),
      Map.entry(SequenceField.NEXT, NEXT),
      Map.entry(SequenceField.ENABLED, ENABLED),
      Map.entry(SequenceField.CREATED_AT, CREATED_AT));

  public static SQLField get(DomainField field) {
    return LOOKUP.get(field);
  }

  public static Map<DomainField, SQLField> all() {
    return LOOKUP;
  }
}
