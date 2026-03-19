package com.lesconstructionssapete.stempyerp.field.sequence;

import java.sql.Types;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.mapper.SQLField;

public final class SequenceField {

  private SequenceField() {
  }

  public static final String CORE_DOMAIN_ENTITY_SEQUENCE = "core_domain_entity_sequence";

  public static final SQLField DOMAIN_ENTITY_ID = new SQLField(
      "domainEntityId",
      CORE_DOMAIN_ENTITY_SEQUENCE,
      "domain_entity_id",
      Types.BIGINT);

  public static final SQLField NEXT = new SQLField(
      "next",
      CORE_DOMAIN_ENTITY_SEQUENCE,
      "next",
      Types.BIGINT);

  public static final SQLField ENABLED = new SQLField(
      "enabled",
      CORE_DOMAIN_ENTITY_SEQUENCE,
      "enabled",
      Types.BOOLEAN);

  public static final SQLField CREATED_AT = new SQLField(
      "createdAt",
      CORE_DOMAIN_ENTITY_SEQUENCE,
      "created_at",
      Types.TIMESTAMP);

  public static final Map<String, SQLField> LOOKUP = Map.ofEntries(
      Map.entry(DOMAIN_ENTITY_ID.logicalName(), DOMAIN_ENTITY_ID),
      Map.entry(NEXT.logicalName(), NEXT),
      Map.entry(ENABLED.logicalName(), ENABLED),
      Map.entry(CREATED_AT.logicalName(), CREATED_AT));

  public static SQLField get(String logicalName) {
    return LOOKUP.get(logicalName);
  }

  public static Map<String, SQLField> all() {
    return LOOKUP;
  }
}
