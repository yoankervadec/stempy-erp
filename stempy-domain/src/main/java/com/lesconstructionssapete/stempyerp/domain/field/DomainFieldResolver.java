package com.lesconstructionssapete.stempyerp.domain.field;

public interface DomainFieldResolver {

  DomainFieldProvider resolve(String logicalName);

}
