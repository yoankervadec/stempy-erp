SELECT
  core_domain_entity_sequence.domain_entity_id,
  core_domain_entity_sequence.next,
  core_domain_entity_sequence.enabled,
  core_domain_entity_sequence.created_at
FROM
  core_domain_entity_sequence
/*WHERE*/
FOR UPDATE;